package com.mioto.pms.aop;

import lombok.Data;

/**
 * @author mioto-qinxj
 * @description
 * @date 2020/3/24
 */
@Data
public class RequestInfo {
    private String ip;
    private String url;
    private String httpMethod;
    private String classMethod;
    private Object requestParams;
    private Long timeCost;
}
