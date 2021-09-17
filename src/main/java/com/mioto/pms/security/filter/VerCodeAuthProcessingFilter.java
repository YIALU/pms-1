package com.mioto.pms.security.filter;

import cn.hutool.core.util.ObjectUtil;
import com.mioto.pms.cache.VerCodeCache;
import com.mioto.pms.module.user.model.Role;
import com.mioto.pms.module.user.model.User;
import com.mioto.pms.module.user.service.RoleService;
import com.mioto.pms.module.user.service.UserService;
import com.mioto.pms.security.PhoneValidException;
import com.mioto.pms.security.VerCodeException;
import com.mioto.pms.utils.SpringBeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author admin
 * @date 2021-08-27 16:27
 */
public class VerCodeAuthProcessingFilter  extends AbstractAuthenticationProcessingFilter {

    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws AuthenticationException, IOException, ServletException {
        String phone = httpServletRequest.getParameter("phone");
        String verCode = httpServletRequest.getParameter("verCode");
        if (SpringBeanUtil.getBean(VerCodeCache.class).codeEquals(verCode,phone)){
            User user = SpringBeanUtil.getBean(UserService.class).findByColumn("phone",phone);
            if (ObjectUtil.isNotNull(user)) {
                List<Role> roles = SpringBeanUtil.getBean(RoleService.class).findRoleByUserId(user.getId());
                Set<SimpleGrantedAuthority> grantedAuthorities = roles.stream().map(role -> new SimpleGrantedAuthority(String.valueOf(role.getId()))).collect(Collectors.toSet());
                return new UsernamePasswordAuthenticationToken(user, new Object(), grantedAuthorities);
            }
            throw new PhoneValidException("手机号不存在");
        }
        throw new VerCodeException("验证码错误");
    }

    public VerCodeAuthProcessingFilter() {
        super(new AntPathRequestMatcher("/verCodeLogin", "POST"));
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
}
