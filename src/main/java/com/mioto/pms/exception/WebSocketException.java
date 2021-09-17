package com.mioto.pms.exception;

import cn.hutool.json.JSONUtil;
import com.mioto.pms.netty.websocket.model.CmdType;
import com.mioto.pms.netty.websocket.model.ReplyMsg;
import com.mioto.pms.result.WsSystemTip;

/**
 * @author admin
 * @date 2021-09-01 15:04
 */
public class WebSocketException  extends RuntimeException{

    private WsSystemTip wsSystemTip;

    private int cmd = CmdType.CMD_REPLY;

    public WebSocketException(WsSystemTip wsSystemTip){
        this.wsSystemTip = wsSystemTip;
    }

    public WebSocketException(int cmd,WsSystemTip wsSystemTip) {
        this.cmd = cmd;
        this.wsSystemTip = wsSystemTip;
    }

    @Override
    public String getMessage() {
        ReplyMsg replyMsg = new ReplyMsg();
        replyMsg.setCode(wsSystemTip.getCode());
        replyMsg.setCmd(cmd);
        replyMsg.setDesc(wsSystemTip.getDesc());
        return JSONUtil.parse(replyMsg).toString();
    }
}
