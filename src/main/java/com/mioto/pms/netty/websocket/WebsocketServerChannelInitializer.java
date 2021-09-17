package com.mioto.pms.netty.websocket;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import java.util.concurrent.TimeUnit;

/**
 * @author admin
 * @date 2021-08-30 15:47
 */
@Component
public class WebsocketServerChannelInitializer extends ChannelInitializer<SocketChannel> {
    @Autowired
    private CloseServerHandler closeServerHandler;
    @Autowired
    private HttpServerHandler httpServerHandler;
    @Autowired
    private WebsocketServerHandler messageServerHandler;

    @Autowired
    private SSLContext sslContext;
    @Value("${wss.started}")
    private int wssStarted;
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        if (wssStarted == 1) {
            SSLEngine sslEngine = sslContext.createSSLEngine();
            sslEngine.setNeedClientAuth(false);
            sslEngine.setUseClientMode(false);
            socketChannel.pipeline().addFirst("sslHandler", new SslHandler(sslEngine));
        }
        //监听超时
        socketChannel.pipeline().addLast(new IdleStateHandler(0, 30, 0, TimeUnit.SECONDS));
        // HttpServerCodec：将请求和应答消息解码为HTTP消息
        socketChannel.pipeline().addLast("http-codec", new HttpServerCodec());
        // HttpObjectAggregator：将HTTP消息的多个部分合成一条完整的HTTP消
        socketChannel.pipeline().addLast("aggregator", new HttpObjectAggregator(65536));
        // ChunkedWriteHandler：向客户端发送HTML5文件
        socketChannel.pipeline().addLast("http-chunked", new ChunkedWriteHandler());
        //关闭后的消息处理
        socketChannel.pipeline().addLast("closeHandler", closeServerHandler);
        //初始化连接的处理handler
        socketChannel.pipeline().addLast("httpHandler", httpServerHandler);
        //消息处理handler
        socketChannel.pipeline().addLast("messageHandler", messageServerHandler);
    }
}