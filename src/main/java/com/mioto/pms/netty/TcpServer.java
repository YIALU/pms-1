package com.mioto.pms.netty;

import com.mioto.pms.netty.protocol.PortEnum;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author qinxj
 * @date 2020/11/6 12:59
 */
@Component
@Slf4j
public class TcpServer {
    @Resource
    private TcpServerChannelInitializer serverChannelInitializer;
    private ChannelFuture channelFuture;
    private EventLoopGroup bossGroup;
    private EventLoopGroup workGroup;

    public void start() {
        bossGroup  = new NioEventLoopGroup(1);
        workGroup = new NioEventLoopGroup();

        ServerBootstrap bootstrap = new ServerBootstrap()
                .group(bossGroup, workGroup)
                //指定通道类型
                .channel(NioServerSocketChannel.class)
                //在ServerChannelInitializer中初始化ChannelPipeline责任链，并添加到serverBootstrap中
                .childHandler(serverChannelInitializer)
                //标识当服务器请求处理线程全满时，用于临时存放已完成三次握手的请求的队列的最大长度
                .option(ChannelOption.SO_BACKLOG, 1024)
                // 是否启用心跳保活机机制
                .childOption(ChannelOption.SO_KEEPALIVE, true);
        try {
            int port = PortEnum.ACQUISITION_TERMINAL.getPort();
            //绑定端口,开始接收进来的连接
            channelFuture = bootstrap.bind(port).sync();
            if (channelFuture.isSuccess()) {
                log.info("tcp server port - {} started!", port);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            Runtime.getRuntime().addShutdownHook(new Thread(() -> shutdown()));
        }
    }

    private void shutdown() {
        if (channelFuture != null) {
            channelFuture.channel().close().syncUninterruptibly();
        }
        if (bossGroup != null) {
            bossGroup.shutdownGracefully();
        }
        if (workGroup != null) {
            workGroup.shutdownGracefully();
        }
    }
}
