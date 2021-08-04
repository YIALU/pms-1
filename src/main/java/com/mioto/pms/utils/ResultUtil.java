package com.mioto.pms.utils;

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletResponse;
import java.io.PrintWriter;

/**
 * @author mioto-qinxj
 * @date 2020/4/23
 */
@Slf4j
public class ResultUtil {
    /**
     * 私有化构造器
     */
    private ResultUtil() {
    }

    /**
     * 使用response输出json
     *
     * @param response
     * @param object
     */
    public static void responseJson(ServletResponse response, Object object) {
        PrintWriter out = null;
        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            out = response.getWriter();
            //保留空值
            out.println(JSONUtil.parseObj(object,false));
        } catch (Exception e) {
            log.error("JSON输出异常:{}", e);
        } finally {
            if (out != null) {
                out.flush();
                out.close();
            }
        }
    }
}
