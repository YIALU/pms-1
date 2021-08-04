package com.mioto.pms.netty;

import cn.hutool.core.util.HexUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.mioto.pms.netty.protocol.PortEnum;
import com.mioto.pms.netty.protocol.Protocol;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;

/**
 * @author qinxj
 * @date 2020/11/6 12:57
 */
@Slf4j
public class TcpServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf in = (ByteBuf)msg;
        byte[] bytes =new byte[in.readableBytes()];
        in.readBytes(bytes);
        in.release();
        InetSocketAddress socketAddress = (InetSocketAddress) ctx.channel().localAddress();
        PortEnum portEnum = PortEnum.getInstance(socketAddress.getPort());
        String recMessage = portEnum.isHex() ? HexUtil.encodeHexStr(bytes) : StrUtil.str(bytes,"gbk");
        //String hostString = ((InetSocketAddress) ctx.channel().remoteAddress()).getHostString();
        log.info("receive message - {} from {}",recMessage, ctx.channel().remoteAddress());
        //根据端口来区分协议类型
        Protocol protocol = PortEnum.getServiceByPort(socketAddress.getPort()).protocolHandler(recMessage);
        log.info("protocol parse object : {} ", JSONUtil.parseObj(protocol,false));
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        InetSocketAddress socketAddress = (InetSocketAddress) ctx.channel().remoteAddress();
        log.info("client - {} connected", socketAddress);
        ChannelUtil.put(socketAddress.getHostString(),ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        InetSocketAddress socketAddress = (InetSocketAddress) ctx.channel().remoteAddress();
        log.info("client - {} disconnected", socketAddress);
        ChannelUtil.remove(socketAddress.getHostString());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        InetSocketAddress socketAddress = (InetSocketAddress) ctx.channel().remoteAddress();
        log.error("tcp server caught exception - {} , close client - {} disconnected",cause.toString(),socketAddress.getHostString());
        ChannelUtil.remove(socketAddress.getHostString());
    }
}
