package com.mioto.pms.netty.tcp;

import cn.hutool.core.util.HexUtil;
import com.mioto.pms.netty.ChannelUtil;
import com.mioto.pms.netty.tcp.protocol.Packet;
import com.mioto.pms.netty.tcp.protocol.ProtocalManagerHelper;
import com.mioto.pms.netty.tcp.protocol.exception.DecodingException;
import com.mioto.pms.netty.tcp.protocol.resolver.MessageResolverFactory;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;

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
        try{
            Packet rgmPacket=new Packet();
        	ProtocalManagerHelper.getProtocalManagerRgm().decode(ByteBuffer.wrap(bytes),rgmPacket);
            log.info("receive message - {} - from {}",HexUtil.encodeHexStr(bytes),ctx.channel().remoteAddress());
        	MessageResolverFactory.getMessageResolver(rgmPacket.getCommand()).resolve(ctx,rgmPacket);
        }catch(DecodingException e){
        	log.error("receive unknown message - {},error - {}",String.valueOf(HexUtil.encodeHex(bytes)),e.getMessage());
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        InetSocketAddress socketAddress = (InetSocketAddress) ctx.channel().remoteAddress();
        log.info("client - {} connected",socketAddress);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        InetSocketAddress socketAddress = (InetSocketAddress) ctx.channel().remoteAddress();
        log.info("client - {} disconnected",socketAddress);
        ChannelUtil.remove(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("tcp server caught exception - {}",cause.toString());
    }
}
