package com.mioto.pms.netty.tcp.protocol.resolver;

import cn.hutool.core.collection.CollUtil;
import com.mioto.pms.netty.ChannelUtil;
import com.mioto.pms.netty.tcp.protocol.Packet;
import com.mioto.pms.netty.tcp.protocol.model.PacketData;
import com.mioto.pms.netty.websocket.WebsocketHelper;
import io.netty.channel.ChannelHandlerContext;

import java.util.List;

/**
 * @author admin
 * @date 2021-07-03 18:11
 */
public abstract class AbstractMessageResolver {

    public abstract void resolve(ChannelHandlerContext ctx, Packet packet) throws Exception;

    /**
     * 回复消息到websocket
     * @param ctx
     * @param packet
     * @return
     */
    protected void reply2Websocket(ChannelHandlerContext ctx, PacketData packet) {
        List<ChannelHandlerContext> wsCtx = ChannelUtil.getWsChannelsByTcpChannel(ctx);
        if (CollUtil.isNotEmpty(wsCtx)) {
            for (ChannelHandlerContext channelHandlerContext : wsCtx) {
                WebsocketHelper.sendMsg(channelHandlerContext, packet);
            }
        }
    }
}
