package com.mioto.pms.module.user.dao;

import com.mioto.pms.module.user.model.MiniProgramUserListVO;
import com.mioto.pms.module.user.model.User;
import com.mioto.pms.module.user.model.UserVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* @auther lzc
* date 2021-04-01 11:13:53
*/
@Repository
public interface UserDao {

    /**
    * 根据条件查询列表
    * @return
    */
    List<UserVO> findList(User user);

    /**
    * 根据列名和对应的值查询对象
    * @param column
    * @param value
    * @return
    */
    UserVO findByColumn(@Param("column") String column, @Param("value") Object value);

    /**
    * 新增对象,忽略空值
    * @return
    */
    int insertIgnoreNull(User user);

    /**
    * 修改对象,忽略空值
    * @return
    */
    int updateIgnoreNull(User user);

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

    int insertUserRole(@Param("userId")Integer userId,@Param("roleId")Integer roleId);

    int updateUserRole(@Param("userId")Integer userId,@Param("roleId")Integer roleId);

    int delUserRoleByUserId(Integer userId);

    int delUserRoleByRoleId(Integer roleId);

    List<MiniProgramUserListVO> findMiniProgramUserList(@Param("name")String name,@Param("phone")String phone,@Param("wxNickname")String wxNickname);
}
