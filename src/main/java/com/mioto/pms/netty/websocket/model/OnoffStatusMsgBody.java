package com.mioto.pms.netty.websocket.model;

import lombok.Getter;
import lombok.Setter;

/**
 * @author admin
 * @date 2021-09-02 15:30
 */
@Getter
@Setter
public class OnoffStatusMsgBody {
    private String focus;

    private int line;
}
