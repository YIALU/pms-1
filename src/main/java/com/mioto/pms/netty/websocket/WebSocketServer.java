package com.mioto.pms.netty.websocket;

import com.mioto.pms.netty.NettyServer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @author mioto-qinxj
 * @date 2020/6/17
 */
@Component
@Slf4j
public class WebSocketServer extends NettyServer {

    /**
     * 自定义服务器通道初始化器
     */
    @Autowired
    private WebsocketServerChannelInitializer websocketServerChannelInitializer;

    @Async
    @Override
    public void start() {
        bossGroup = new NioEventLoopGroup(1);
        workGroup = new NioEventLoopGroup();
        //创建主线程一个主线程
        ServerBootstrap bootstrap = new ServerBootstrap()
                .group(bossGroup, workGroup)
                //指定通道类型
                .channel(NioServerSocketChannel.class)
                //在ServerChannelInitializer中初始化ChannelPipeline责任链，并添加到serverBootstrap中
                .childHandler(websocketServerChannelInitializer)
                //标识当服务器请求处理线程全满时，用于临时存放已完成三次握手的请求的队列的最大长度
                .option(ChannelOption.SO_BACKLOG, 1024)
                // 是否启用心跳保活机机制
                .childOption(ChannelOption.SO_KEEPALIVE, true);
        try {
            //绑定端口,开始接收进来的连接
            channelFuture = bootstrap.bind(port).sync();
            if (channelFuture.isSuccess()) {
                log.info("websocket server started! server port is: {}", port);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            Runtime.getRuntime().addShutdownHook(new Thread(() -> shutdown()));
        }
    }

    @Override
    public void shutdown() {
        super.shutdown();
        log.info("websocket server destroy");
    }

    @Override
    public void restart() {
       super.restart();
    }

    @Override
    @Value("${server.websocket.port}")
    protected void setPort(int port) {
        super.setPort(port);
    }
}
