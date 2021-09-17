package com.mioto.pms.result;

/**
 * @author admin
 * @date 2021-09-01 15:00
 */
public enum WsSystemTip {

    OK("100000","OK"),
    ILLEGAL("100001","消息不合法"),
    CLIENT_TYPE_ERROR("100002","客户端类型错误"),
    CMD_ERROR("100003","指令类型错误"),
    AUTH_ERROR("100004","客户端必须完成认证"),
    DEVICE_OFFLINE("100005","设备离线");

    private String code;
    private String desc;

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    WsSystemTip(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
