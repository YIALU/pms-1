package com.mioto.pms.netty.websocket.resolver;

import com.mioto.pms.netty.ChannelUtil;
import com.mioto.pms.netty.websocket.WebsocketHelper;
import com.mioto.pms.netty.websocket.model.CmdType;
import com.mioto.pms.netty.websocket.model.ReceiveMsg;
import com.mioto.pms.result.WsSystemTip;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author admin
 * @date 2021-08-30 16:04
 */
public class AuthMsgResolver extends WsMessageResolver {

    @Override
    public void resolve(ChannelHandlerContext ctx, ReceiveMsg msg) {
        ChannelUtil.putWs(msg.getClientType(),msg.getUserId(),ctx);
        WebsocketHelper.sendMsg(ctx, CmdType.CMD_REPLY, WsSystemTip.OK);
    }
}
