package com.mioto.pms.netty.websocket;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshakerFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author admin
 * @date 2021-08-30 15:52
 */
@Component
@ChannelHandler.Sharable
@Slf4j
public class HttpServerHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, FullHttpRequest fullHttpRequest) throws Exception {
        log.info("create websocket client channel:{}", channelHandlerContext.channel().id().asLongText());
        WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory(
                "ws://" + fullHttpRequest.headers().get(HttpHeaderNames.HOST) + fullHttpRequest.uri(),
                null, false);
        WebSocketServerHandshaker handShaker = wsFactory.newHandshaker(fullHttpRequest);
        if (handShaker == null) {
            WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(channelHandlerContext.channel());
        } else {
            handShaker.handshake(channelHandlerContext.channel(), fullHttpRequest);
        }
    }
}
