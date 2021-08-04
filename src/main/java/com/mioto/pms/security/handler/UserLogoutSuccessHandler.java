package com.mioto.pms.security.handler;


import com.mioto.pms.result.ResultData;
import com.mioto.pms.utils.ResultUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 用户登出类
 *
 * @author mioto-qinxj
 * @date 2019/12/4
 */
@Component
public class UserLogoutSuccessHandler implements LogoutSuccessHandler {
    /**
     * 用户登出返回结果
     * 前端也应该清楚掉token
     */
    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        SecurityContextHolder.clearContext();
        ResultUtil.responseJson(httpServletResponse, ResultData.success());
    }
}
