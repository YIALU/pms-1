package com.mioto.pms.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import org.springframework.stereotype.Component;

/**
 * @author qinxj
 * @date 2020/11/6 13:00
 */
@Component
public class TcpServerChannelInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        //接收消息处理
        pipeline.addLast(new com.mioto.pms.netty.TcpServerHandler());
    }
}
