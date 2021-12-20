package com.mioto.pms.module.user.service;


import com.mioto.pms.module.user.model.MiniProgramUserListVO;
import com.mioto.pms.module.user.model.User;
import com.mioto.pms.module.user.model.UserDTO;
import com.mioto.pms.module.user.model.UserVO;

import java.util.List;

/**
* @auther lzc
* date 2021-04-01 11:13:53
*/
public interface UserService {
    /**
    * 根据条件查询列表
    */
    List<UserVO> findList(User user);

    /**
    * 根据列名和对应的值查询对象
    */
    UserVO findByColumn(String column, Object value);

    /**
    * 新增对象,忽略空值
    */
    int insertIgnoreNull(UserDTO user);

    /**
    * 修改对象,忽略空值
    */
    int updateIgnoreNull(UserDTO user);

    /**
    * 根据列名和对应的值删除对象
    */
    int deleteByColumn(String column, Object value);

    /**
    * 根据主键列表批量删除
    */
    int batchDelete(int[] ids);

    List<MiniProgramUserListVO> findMiniProgramUserList(String name,String phone,String wxNickname);
}
