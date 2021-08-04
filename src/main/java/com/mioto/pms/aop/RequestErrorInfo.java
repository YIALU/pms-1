package com.mioto.pms.aop;

import lombok.Data;

/**
 * @author mioto-qinxj
 * @description 异常请求信息
 * @date 2020/3/24
 */
@Data
public class RequestErrorInfo {
    private String ip;
    private String url;
    private String httpMethod;
    private String classMethod;
    private Object requestParams;
    private String errorMsg;
}
