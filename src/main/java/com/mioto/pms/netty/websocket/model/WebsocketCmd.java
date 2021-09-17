package com.mioto.pms.netty.websocket.model;

import com.mioto.pms.netty.websocket.resolver.*;
import org.apache.commons.lang3.ObjectUtils;

/**
 * @author admin
 * @date 2021-08-30 15:55
 */
public enum WebsocketCmd {
    AUTH(CmdType.CMD_AUTH,new AuthMsgResolver()),
    METER_READING(CmdType.CMD_METER_READING,new MeterReadingResolver()),
    AMMETER_ON_OFF(CmdType.CMD_AM_METER_ON_OFF,new AmmeterOnOffResolver()),
    WATER_METER_ON_OFF(CmdType.CMD_WATER_METER_ON_OFF,new WaterMeterOnOffResolver()),
    ON_OFF_STATUS(CmdType.CMD_ON_OFF_STATUS,new OnoffStatusResolver()),
    DEFAULT(CmdType.CMD_DEFAULT,new DefaultMsgResolver());

    public static WebsocketCmd get(final Integer cmd){
        for (WebsocketCmd value : WebsocketCmd.values()) {
            if (ObjectUtils.equals(cmd,value.cmd)){
                return value;
            }
        }
        return DEFAULT;
    }

    private Integer cmd;
    private WsMessageResolver resolver;

    public WsMessageResolver getResolver() {
        return resolver;
    }

    WebsocketCmd(Integer cmd, WsMessageResolver resolver) {
        this.cmd = cmd;
        this.resolver = resolver;
    }
}
