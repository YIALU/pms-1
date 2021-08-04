package com.mioto.pms.security.handler;



import com.mioto.pms.result.ResultData;
import com.mioto.pms.result.SystemTip;
import com.mioto.pms.utils.ResultUtil;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author mioto-qinxj
 * @date 2019/12/4
 */
@Component
public class UserAuthAccessDeniedHandler implements AccessDeniedHandler {
    /**
     * 暂无权限返回结果
     */
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        ResultUtil.responseJson(httpServletResponse, ResultData.result(SystemTip.UNAUTHORIZED));
    }
}
