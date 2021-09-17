package com.mioto.pms.netty;

import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;

/**
 * @author admin
 * @date 2021-08-30 15:45
 */
public abstract class NettyServer {
    /**
     * 处理Accept连接事件的线程，这里线程数设置为1即可，netty处理链接事件默认为单线程，过度设置反而浪费cpu资源
     */
    protected EventLoopGroup bossGroup;
    /**
     * 处理handler的工作线程，其实也就是处理IO读写 。线程数据默认为 CPU 核心数乘以2
     */
    protected EventLoopGroup workGroup;
    protected ChannelFuture channelFuture;

    protected int port;
    /**
     * 服务启动
     */
    public abstract void start();

    /**
     * 销毁服务
     */
    protected void shutdown() {
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

    /**
     * 重启服务
     */
    protected void restart() {
        shutdown();
        start();
    }

    protected void setPort(int port){
        this.port = port;
    }
}
