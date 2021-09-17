package com.mioto.pms.netty.websocket.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author admin
 * @date 2021-08-30 16:02
 */
@Getter
@Setter
@ToString
public class ReceiveMsg {
    private Integer cmd;
    private String clientType;
    private int userId;
    private Object body;
}
