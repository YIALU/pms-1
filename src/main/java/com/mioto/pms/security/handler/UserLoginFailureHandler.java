package com.mioto.pms.security.handler;



import com.mioto.pms.result.ResultData;
import com.mioto.pms.result.SystemTip;
import com.mioto.pms.utils.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录失败处理类
 *
 * @author mioto-qinxj
 * @date 2019/12/4
 */
@Component
@Slf4j
public class UserLoginFailureHandler implements AuthenticationFailureHandler {
    /**
     * 登录失败返回结果
     */
    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {

        SystemTip systemTip = SystemTip.LOGIN_FAILURE;

        if (e instanceof UsernameNotFoundException) {
            systemTip = SystemTip.USERNAME_PASSWORD_ERROR;
        } else if (e instanceof LockedException) {
            systemTip = SystemTip.USER_LOCKED;
        } else if (e instanceof BadCredentialsException) {
            systemTip = SystemTip.USERNAME_PASSWORD_ERROR;
        } else if (e instanceof DisabledException) {
            systemTip = SystemTip.USER_DISABLED;
        }

        ResultUtil.responseJson(httpServletResponse, ResultData.result(systemTip));
    }
}
