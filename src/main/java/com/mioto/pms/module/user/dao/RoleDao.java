package com.mioto.pms.module.user.dao;


import com.mioto.pms.module.user.model.Function;
import com.mioto.pms.module.user.model.Role;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* @auther lzc
* date 2021-04-01 16:06:39
*/
@Repository
public interface RoleDao {

    /**
    * 根据条件查询列表
    * @return
    */
    List<Role> findList(Role role);

    /**
    * 根据列名和对应的值查询对象
    * @param column
    * @param value
    * @return
    */
    Role findByColumn(@Param("column") String column, @Param("value") Object value);

    /**
    * 新增对象,忽略空值
    * @return
    */
    int insertIgnoreNull(Role role);

    /**
    * 修改对象,忽略空值
    * @return
    */
    int updateIgnoreNull(Role role);

    /**
    * 根据列名和对应的值删除对象
    * @param column
    * @param value
    * @return
    */
    int deleteByColumn(@Param("column") String column, @Param("value") Object value);

    /**
    * 根据主键列表批量删除
    * @param ids
    * @return
    */
    int batchDelete(int[] ids);

    int insertRoleFunction(@Param("role") Role role,@Param("list") List<Function> list);

    List<Role> findByUserId(int id);
}
