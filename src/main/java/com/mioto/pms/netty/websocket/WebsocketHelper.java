package com.mioto.pms.netty.websocket;

import cn.hutool.json.JSONUtil;
import com.mioto.pms.netty.websocket.model.ReplyMsg;
import com.mioto.pms.result.WsSystemTip;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;

/**
 * @author admin
 * @date 2021-09-01 15:14
 */
@Slf4j
public class WebsocketHelper {
    public static void sendMsg(ChannelHandlerContext ctx, Object body){
        ReplyMsg msg = new ReplyMsg();
        msg.setBody(body);
        sendMsg(ctx,msg);
    }


    public static void sendMsg(ChannelHandlerContext ctx, int cmd, WsSystemTip systemTip){
        ReplyMsg msg = new ReplyMsg();
        msg.setCmd(cmd);
        msg.setCode(systemTip.getCode());
        msg.setDesc(systemTip.getDesc());
        sendMsg(ctx,msg);
    }

    public static void sendMsg(ChannelHandlerContext ctx, ReplyMsg msg){
        String message = JSONUtil.parse(msg).toString();
        sendMsg(ctx,message);
    }

    public static void sendMsg(ChannelHandlerContext ctx, String message){
        ctx.writeAndFlush(new TextWebSocketFrame(message));
        log.info("send websocket message - {} to - {}", message, ctx.channel().remoteAddress());
    }
}
