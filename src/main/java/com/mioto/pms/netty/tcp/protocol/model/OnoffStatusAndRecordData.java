package com.mioto.pms.netty.tcp.protocol.model;

import lombok.Getter;
import lombok.Setter;

/**
 * @author admin
 * @date 2021-09-02 15:50
 */
@Getter
@Setter
public class OnoffStatusAndRecordData extends PacketData{
    /**
     * 合断闸状态 0-合闸 1-断闸
     */
    private int status;
    private int line;
    private String focus;
    /**
     * 设备在线状态 0-离线 1-在线
     */
    private int onlineStatus;
}
