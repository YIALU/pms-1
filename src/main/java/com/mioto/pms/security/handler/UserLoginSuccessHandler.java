package com.mioto.pms.security.handler;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.mioto.pms.module.user.model.User;
import com.mioto.pms.result.ResultData;
import com.mioto.pms.security.utils.JwtTokenUtil;
import com.mioto.pms.utils.ResultUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录成功处理类
 *
 * @author mioto-qinxj
 * @date 2019/12/4
 */
@Component
public class UserLoginSuccessHandler implements AuthenticationSuccessHandler {

    /**
     * 登录成功返回结果
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        User user = (User) authentication.getPrincipal();
        JSONObject logonUser = JSONUtil.parseObj(user,false);
        logonUser.remove("password");
        JSONObject result = JSONUtil.createObj()
                .set("token", JwtTokenUtil.createAccessToken(user,authentication.getAuthorities()))
                .set("logonUser",logonUser)
                .set("role",authentication.getAuthorities().stream().findFirst().get().toString());
        ResultUtil.responseJson(httpServletResponse, ResultData.success(result));
    }
}
