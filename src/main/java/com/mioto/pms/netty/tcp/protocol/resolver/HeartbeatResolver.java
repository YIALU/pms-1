package com.mioto.pms.netty.tcp.protocol.resolver;

import com.mioto.pms.netty.tcp.TcpHelper;
import com.mioto.pms.netty.tcp.protocol.BinPacket;
import com.mioto.pms.netty.tcp.protocol.Packet;
import com.mioto.pms.netty.tcp.protocol.ProtocalManagerHelper;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author admin
 * @date 2021-07-05 15:46
 */
public class HeartbeatResolver extends AbstractMessageResolver {

    @Override
    public void resolve(ChannelHandlerContext ctx, Packet packet) throws Exception {
        Packet rgmPacket=new Packet();
        rgmPacket.setCommand("Confirm");
        rgmPacket.setTerminalAddress(packet.getTerminalAddress());
        rgmPacket.setLine(0);
        BinPacket binPacket = new BinPacket();
        ProtocalManagerHelper.getProtocalManagerRgm().encode(rgmPacket,binPacket);
        TcpHelper.sendMessage(ctx,binPacket);
    }
}
