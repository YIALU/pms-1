package com.mioto.pms.module.user.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNode;
import cn.hutool.core.lang.tree.TreeNodeConfig;
import cn.hutool.core.lang.tree.TreeUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.IdUtil;
import com.mioto.pms.module.user.dao.FunctionDao;
import com.mioto.pms.module.user.model.Function;
import com.mioto.pms.module.user.model.FunctionVO;
import com.mioto.pms.module.user.service.FunctionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* @auther lzc
* date 2021-04-01 16:24:10
*/
@Service("functionService")
public class FunctionServiceImpl implements FunctionService {

    @Resource
    private FunctionDao functionDao;

    /**
    * 根据条件查询列表
    */
    @Override
    public List<Function> findList(Function function) {
        return functionDao.findList(function);
    }

    /**
    * 根据列名和对应的值查询对象
    */
    @Override
    public Function findByColumn(String column, String value) {
        return functionDao.findByColumn(column, value);
    }

    /**
    * 新增对象,忽略空值
    */
    @Override
    public int insertIgnoreNull(Function function) {
         return functionDao.insertIgnoreNull(function);
    }

    /**
    * 修改对象,忽略空值
    */
    @Override
    public int updateIgnoreNull(Function function) {
        return functionDao.updateIgnoreNull(function);
    }

    /**
    * 根据列名和对应的值删除对象
    */
    @Override
    public int deleteByColumn(String column, String value) {
        return functionDao.deleteByColumn(column,value);
    }

    /**
    * 根据主键列表批量删除
    */
    @Override
    public int batchDelete(String[] ids) {
        return functionDao.batchDelete(ids);
    }

    @Override
    public List<Tree<Integer>> findTree(Integer roleId) {
        List<FunctionVO> functionList = functionDao.findByRoleId(roleId);
        List<TreeNode<Integer>> nodeList = CollUtil.newArrayList();
        Map<String,Object> map;
        TreeNode<Integer> node;
        for (FunctionVO function: functionList){
            node = new TreeNode(function.getId(),function.getPid(),function.getName(),function.getSortNo());
            map = new HashMap<>(1);
            map.put("selected",function.getSelected());
            node.setExtra(map);
            nodeList.add(node);
        }
        TreeNodeConfig treeNodeConfig = new TreeNodeConfig();
        treeNodeConfig.setWeightKey("sortNo");
        treeNodeConfig.setParentIdKey("pid");
        return TreeUtil.build(nodeList,0,treeNodeConfig,
                (treeNode,tree) ->{
                    tree.setId(treeNode.getId());
                    tree.setParentId(treeNode.getParentId());
                    tree.setName(treeNode.getName());
                    tree.setWeight(treeNode.getWeight());
                    tree.putExtra("selected",treeNode.getExtra().get("selected"));
                });
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updatePermission(Integer roleId, Integer[] functionIds) {
        int result = functionDao.delPermissions(roleId);
        if (ArrayUtil.isNotEmpty(functionIds)){
            return functionDao.insertPermissions(roleId,functionIds);
        }
        return result;
    }
}
