package com.mioto.pms.netty.tcp.protocol.resolver;


import com.mioto.pms.netty.ChannelUtil;
import com.mioto.pms.netty.tcp.TcpHelper;
import com.mioto.pms.netty.tcp.protocol.BinPacket;
import com.mioto.pms.netty.tcp.protocol.Packet;
import com.mioto.pms.netty.tcp.protocol.ProtocalManagerHelper;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;

/**
 * device login packet resolver
 * @author admin
 * @date 2021-07-03 18:20
 */
@Slf4j
public class LoginResolver extends AbstractMessageResolver {
    @Override
    public void resolve(ChannelHandlerContext ctx, Packet packet) throws Exception {
        Packet rgmPacket=new Packet();
        rgmPacket.setCommand("Confirm");
        rgmPacket.setTerminalAddress(packet.getTerminalAddress());
        rgmPacket.setLine(0);
        rgmPacket.setData(new HashMap<>(0));
        BinPacket binPacket = new BinPacket();
        ProtocalManagerHelper.getProtocalManagerRgm().encode(rgmPacket,binPacket);
        TcpHelper.sendMessage(ctx,binPacket);
        
        ChannelUtil.put(packet.getTerminalAddress(),ctx);
    }
}
