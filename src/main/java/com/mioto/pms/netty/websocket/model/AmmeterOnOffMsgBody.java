package com.mioto.pms.netty.websocket.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author admin
 * @date 2021-09-01 18:57
 */
@Getter
@Setter
@ToString
public class AmmeterOnOffMsgBody {

    private String focus;

    private boolean switchStatus;

    private String deviceId;
}
