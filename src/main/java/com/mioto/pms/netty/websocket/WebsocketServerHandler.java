package com.mioto.pms.netty.websocket;

import cn.hutool.json.JSONException;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.mioto.pms.exception.WebSocketException;
import com.mioto.pms.netty.websocket.model.CmdType;
import com.mioto.pms.netty.websocket.model.ReceiveMsg;
import com.mioto.pms.netty.websocket.model.WebsocketCmd;
import com.mioto.pms.result.WsSystemTip;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author admin
 * @date 2021-08-30 15:52
 */
@Component
@ChannelHandler.Sharable
@Slf4j
public class WebsocketServerHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame textWebSocketFrame) throws Exception {
        try {
            JSONObject jsonObject = JSONUtil.parseObj(textWebSocketFrame.text());
            ReceiveMsg receiveMsg = jsonObject.toBean(ReceiveMsg.class);
            log.info("receive websocket message - {}",jsonObject);
            WebsocketCmd.get(receiveMsg.getCmd()).getResolver().process(channelHandlerContext,receiveMsg);
        }catch (JSONException e) {
            log.error("websocket数据格式错误", e.getMessage());
            throw new WebSocketException(CmdType.CMD_REPLY, WsSystemTip.ILLEGAL);
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        if (cause instanceof WebSocketException){
            WebsocketHelper.sendMsg(ctx,cause.getMessage());
        }
    }
}
