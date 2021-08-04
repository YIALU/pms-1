package com.mioto.pms.module.user.service;


import cn.hutool.core.lang.tree.Tree;
import com.mioto.pms.module.user.model.Function;
import java.util.List;

/**
* @auther lzc
* date 2021-04-01 16:24:10
*/
public interface FunctionService {
/**
* 根据条件查询列表
*/
List<Function> findList(Function function);

    /**
    * 根据列名和对应的值查询对象
    */
    Function findByColumn(String column, String value);

    /**
    * 新增对象,忽略空值
    */
    int insertIgnoreNull(Function function);

    /**
    * 修改对象,忽略空值
    */
    int updateIgnoreNull(Function function);

    /**
    * 根据列名和对应的值删除对象
    */
    int deleteByColumn(String column, String value);

    /**
    * 根据主键列表批量删除
    */
    int batchDelete(String[] ids);

    List<Tree<Integer>> findTree(Integer roleId);

    int updatePermission(Integer roleId, Integer[] functionIds);
}
