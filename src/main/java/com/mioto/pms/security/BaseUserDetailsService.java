package com.mioto.pms.security;

import cn.hutool.core.util.ObjectUtil;
import com.mioto.pms.module.user.model.User;
import com.mioto.pms.module.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * @author admin
 * @date 2021-08-09 15:02
 */
//@Component
public class BaseUserDetailsService implements UserDetailsService {
    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userService.findByColumn("username",s);
        BaseUserDetails userDetails = new BaseUserDetails();
        if (ObjectUtil.isNotEmpty(user)){
            userDetails.setUsername(user.getUsername());
            userDetails.setPassword(user.getPassword());
            return userDetails;
        }
        throw new UsernameNotFoundException("用户名不存在");
    }
}
