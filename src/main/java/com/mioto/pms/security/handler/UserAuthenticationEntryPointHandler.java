package com.mioto.pms.security.handler;



import com.mioto.pms.result.ResultData;
import com.mioto.pms.result.SystemTip;
import com.mioto.pms.utils.ResultUtil;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 用户未登录处理类
 *
 * @author mioto-qinxj
 * @date 2019/12/4
 */
@Component
public class UserAuthenticationEntryPointHandler implements AuthenticationEntryPoint {
    /**
     * 用户未登录返回结果
     */
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {

        ResultUtil.responseJson(httpServletResponse, ResultData.result(SystemTip.NOT_LOGGED_IN));
    }
}
