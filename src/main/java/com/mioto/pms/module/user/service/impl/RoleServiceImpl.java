package com.mioto.pms.module.user.service.impl;

import cn.hutool.core.util.StrUtil;
import com.mioto.pms.module.user.dao.FunctionDao;
import com.mioto.pms.module.user.dao.RoleDao;
import com.mioto.pms.module.user.dao.UserDao;
import com.mioto.pms.module.user.model.Role;
import com.mioto.pms.module.user.service.RoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
* @auther lzc
* date 2021-04-01 16:06:39
*/
@Service("roleService")
public class RoleServiceImpl implements RoleService {

    @Resource
    private RoleDao roleDao;
    @Resource
    private FunctionDao functionDao;
    @Resource
    private UserDao userDao;

    /**
    * 根据条件查询列表
    */
    @Override
    public List<Role> findList(Role role) {
        return roleDao.findList(role);
    }

    /**
    * 根据列名和对应的值查询对象
    */
    @Override
    public Role findByColumn(String column, Object value) {
        return roleDao.findByColumn(column, value);
    }

    /**
    * 新增对象,忽略空值
    */
    @Override
    public int insertIgnoreNull(Role role) {
        role.setCreateTime(new Date());
        return roleDao.insertIgnoreNull(role);
    }

    /**
    * 修改对象,忽略空值
    */
    @Override
    public int updateIgnoreNull(Role role) {
        return roleDao.updateIgnoreNull(role);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int deleteByColumn(String column, Object value) {
        if (StrUtil.equals(column,"id")){
            functionDao.delPermissions((Integer) value);
            userDao.delUserRoleByRoleId((Integer) value);
        }else {
            int roleId = roleDao.findByColumn(column,value).getId();
            functionDao.delPermissions(roleId);
            userDao.delUserRoleByRoleId(roleId);
        }
        return roleDao.deleteByColumn(column,value);
    }

    /**
    * 根据主键列表批量删除
    */
    @Override
    public int batchDelete(int[] ids) {
        return roleDao.batchDelete(ids);
    }

    @Override
    public List<Role> findRoleByUserId(int id) {
        return roleDao.findByUserId(id);
    }
    @Override
    public boolean isExistUser(int roleId){
        Object obj = roleDao.isExistUser(roleId);

        return obj instanceof Boolean ? true : false;
    }

}
