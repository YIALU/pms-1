package com.mioto.pms.netty.websocket;

import cn.hutool.core.util.StrUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author admin
 * @date 2021-08-30 15:48
 */
@Component
@ChannelHandler.Sharable
@Slf4j
public class CloseServerHandler extends SimpleChannelInboundHandler<CloseWebSocketFrame> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, CloseWebSocketFrame closeWebSocketFrame) throws Exception {
        AttributeKey<String> key = AttributeKey.valueOf("userId");
        String userId = ctx.channel().attr(key).get();
        if(StrUtil.isNotEmpty(userId)){
            log.info("断开用户id - {} 的websocket客户端连接",userId);
        }
        ctx.close();
    }
}
