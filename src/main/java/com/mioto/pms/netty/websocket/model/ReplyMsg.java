package com.mioto.pms.netty.websocket.model;

import com.mioto.pms.result.WsSystemTip;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author admin
 * @date 2021-09-01 14:58
 */
@Getter
@Setter
@ToString
public class ReplyMsg {
    private int cmd = CmdType.CMD_REPLY;

    private String code = WsSystemTip.OK.getCode();

    private String desc= WsSystemTip.OK.getDesc();

    private Object body;
}
