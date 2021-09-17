package com.mioto.pms.netty.websocket.model;

/**
 * @author admin
 * @date 2021-09-01 15:59
 */
public class CmdType {

    public static final int CMD_AUTH = 1;
    public static final int CMD_METER_READING = 2;
    public static final int CMD_AM_METER_ON_OFF = 3;
    public static final int CMD_WATER_METER_ON_OFF = 4;
    public static final int CMD_ON_OFF_STATUS = 5;
    public static final int CMD_DEFAULT = 0;

    public static final int CMD_REPLY = 100;
}
