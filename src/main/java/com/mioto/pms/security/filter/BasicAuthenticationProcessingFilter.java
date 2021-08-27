package com.mioto.pms.security.filter;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.mioto.pms.module.user.model.Role;
import com.mioto.pms.module.user.model.User;
import com.mioto.pms.module.user.service.RoleService;
import com.mioto.pms.module.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author mioto-qinxj
 * @date 2019/12/4
 */
@Component
public class BasicAuthenticationProcessingFilter extends AbstractAuthenticationProcessingFilter {
    @Resource
    private RoleService roleService;
    @Resource
    private UserService userService;
   /* @Autowired
    private DataSource dataSource;
    @Autowired
    private BaseUserDetailsService baseUserDetailsService;*/
    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws AuthenticationException, IOException, ServletException {
        String username = httpServletRequest.getParameter("username");
        String password = httpServletRequest.getParameter("password");
        User user = userService.findByColumn("username",username);
        if (ObjectUtil.isNotNull(user)) {
                if (!new BCryptPasswordEncoder().matches(password, user.getPassword())) {
                    throw new BadCredentialsException("密码错误");
                }
                List<Role> roles = roleService.findRoleByUserId(user.getId());
                if (CollUtil.isEmpty(roles)){
                    throw new AuthenticationServiceException("无角色,禁止登入");
                }
                Set<SimpleGrantedAuthority> grantedAuthorities = roles.stream().map(role -> new SimpleGrantedAuthority(String.valueOf(role.getId()))).collect(Collectors.toSet());
                return new UsernamePasswordAuthenticationToken(user, new Object(), grantedAuthorities);
            }

        throw new UsernameNotFoundException("用户名不存在");
    }

    @Autowired
    @Override
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
    }

    public BasicAuthenticationProcessingFilter() {
        super(new AntPathRequestMatcher("/login", "POST"));
    }

    @Override
    @Autowired
    public void setAuthenticationSuccessHandler(AuthenticationSuccessHandler successHandler) {
        super.setAuthenticationSuccessHandler(successHandler);
    }

    @Override
    @Autowired
    public void setAuthenticationFailureHandler(AuthenticationFailureHandler failureHandler) {
        super.setAuthenticationFailureHandler(failureHandler);
    }

  /*  @Autowired
    @Override
    public void setRememberMeServices(RememberMeServices rememberMeServices) {
        super.setRememberMeServices(rememberMeServices);
    }

    @Bean
    public RememberMeServices rememberMeServices(){
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        PersistentTokenBasedRememberMeServices rememberMeServices =
                new PersistentTokenBasedRememberMeServices("INTERNAL_SECRET_KEY",baseUserDetailsService,jdbcTokenRepository);
        rememberMeServices.setParameter("remember-me");
        return rememberMeServices;
    }*/

}
