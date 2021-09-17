package com.mioto.pms.netty.websocket.resolver;

import com.mioto.pms.exception.WebSocketException;
import com.mioto.pms.netty.websocket.model.CmdType;
import com.mioto.pms.netty.websocket.model.ReceiveMsg;
import com.mioto.pms.result.WsSystemTip;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * @author admin
 * @date 2021-08-30 16:13
 */
@Slf4j
public class DefaultMsgResolver extends WsMessageResolver {

    @Override
    public void resolve(ChannelHandlerContext ctx, ReceiveMsg msg) {
        log.error("不支持的消息类型 - {}",msg);
        throw new WebSocketException(CmdType.CMD_REPLY, WsSystemTip.CMD_ERROR);
    }
}
