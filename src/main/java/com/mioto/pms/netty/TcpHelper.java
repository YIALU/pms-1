package com.mioto.pms.netty;

import cn.hutool.core.util.HexUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * @author qinxj
 * @date 2020/11/6 14:52
 */
@Slf4j
public class TcpHelper {

    public static void sendMessage(ChannelHandlerContext ctx,String message){
        ByteBuf buffer = Unpooled.buffer();
        buffer.writeBytes(HexUtil.decodeHex(message));
        log.info("send message - {} to {}",message,ctx.channel().localAddress());
        ctx.writeAndFlush(buffer);
    }
}
