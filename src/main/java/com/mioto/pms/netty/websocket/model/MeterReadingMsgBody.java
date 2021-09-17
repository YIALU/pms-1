package com.mioto.pms.netty.websocket.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author admin
 * @date 2021-09-01 16:05
 */
@Getter
@Setter
@ToString
public class MeterReadingMsgBody{
    private String roomId;
}
