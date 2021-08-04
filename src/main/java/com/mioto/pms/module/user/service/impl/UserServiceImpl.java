package com.mioto.pms.module.user.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.mioto.pms.module.user.dao.UserDao;
import com.mioto.pms.module.user.model.MiniProgramUserListVO;
import com.mioto.pms.module.user.model.User;
import com.mioto.pms.module.user.model.UserDTO;
import com.mioto.pms.module.user.model.UserVO;
import com.mioto.pms.module.user.service.UserService;
import com.mioto.pms.utils.BaseUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
* @auther lzc
* date 2021-04-01 11:13:53
*/
@Service("userService")
public class UserServiceImpl implements UserService {

    @Resource
    private UserDao userDao;

    /**
    * 根据条件查询列表
    */
    @Override
    public List<UserVO> findList(User user) {
        return userDao.findList(user);
    }

    /**
    * 根据列名和对应的值查询对象
    */
    @Override
    public UserVO findByColumn(String column, Object value) {
        return userDao.findByColumn(column, value);
    }

    /**
    * 新增对象,忽略空值
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertIgnoreNull(UserDTO user) {
        if(StrUtil.isNotBlank(user.getPassword())){
            user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        }
        if (StrUtil.isEmpty(user.getName())){
            user.setName(user.getNickname());
        }
        userDao.insertIgnoreNull(user);
        return userDao.insertUserRole(user.getId(),user.getRoleId());
    }

    /**
    * 修改对象,忽略空值
    */
    @Override
    public int updateIgnoreNull(UserDTO user) {
        int result  = 0;
        if (ObjectUtil.isNotEmpty(user.getRoleId())){
            result = userDao.updateUserRole(user.getId(),user.getRoleId());
        }
        if (!BaseUtil.checkObjIsNullIgnoreId(user,true)){
            result = userDao.updateIgnoreNull(user);
        }
        return result;
    }

    /**
    * 根据列名和对应的值删除对象
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteByColumn(String column, Object value) {
        if (StrUtil.equals("id",column)){
            userDao.delUserRoleByUserId((Integer) value);
        }else {
            Integer id = findByColumn(column,value).getId();
            userDao.delUserRoleByUserId(id);
        }
        return userDao.deleteByColumn(column,value);
    }

    /**
    * 根据主键列表批量删除
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int batchDelete(int[] ids) {
        int result = userDao.batchDelete(ids);
        Arrays.stream(ids).forEach(id ->{
            userDao.delUserRoleByUserId(id);
        });

        return result;
    }

    @Override
    public List<MiniProgramUserListVO> findMiniProgramUserList() {
        return userDao.findMiniProgramUserList();
    }
}
