package com.mioto.pms.module.user.service;



import com.mioto.pms.module.user.model.Role;

import java.util.List;

/**
* @auther lzc
* date 2021-04-01 16:06:39
*/
public interface RoleService {
/**
* 根据条件查询列表
*/
List<Role> findList(Role role);

    /**
    * 根据列名和对应的值查询对象
    */
    Role findByColumn(String column, Object value);

    /**
    * 新增对象,忽略空值
    */
    int insertIgnoreNull(Role role);

    /**
    * 修改对象,忽略空值
    */
    int updateIgnoreNull(Role role);

    /**
    * 根据列名和对应的值删除对象
    */
    int deleteByColumn(String column, Object value);

    /**
    * 根据主键列表批量删除
    */
    int batchDelete(int[] ids);

    List<Role> findRoleByUserId(int id);

    /**
     * 根据角色id查询是否有用户存在
     * @param roleId
     * @return
     */
    boolean isExistUser(int roleId);
}
