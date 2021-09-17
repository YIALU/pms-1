package com.mioto.pms.netty.tcp.protocol.resolver;

import cn.hutool.core.convert.Convert;
import com.mioto.pms.netty.ChannelUtil;
import com.mioto.pms.netty.tcp.protocol.Packet;
import com.mioto.pms.netty.tcp.protocol.model.OnoffStatusAndRecordData;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * 通断电状态及纪录
 * @author admin
 * @date 2021-07-05 15:46
 */
@Slf4j
public class OnoffStatusAndRecordResolver extends AbstractMessageResolver {

    @Override
    public void resolve(ChannelHandlerContext ctx, Packet packet) throws Exception {
        log.info("通断电状态及纪录 - {}", packet);
        OnoffStatusAndRecordData packetData = Convert.convert(OnoffStatusAndRecordData.class,packet.getData());
        packetData.setFocus(packet.getTerminalAddress());
        packetData.setLine(packet.getLine());
        packetData.setOnlineStatus(ChannelUtil.containsKey(packet.getTerminalAddress()) ? 1 : 0);
        reply2Websocket(ctx,packetData);
    }
}
