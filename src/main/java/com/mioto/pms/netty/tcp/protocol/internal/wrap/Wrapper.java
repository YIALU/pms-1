package com.mioto.pms.netty.tcp.protocol.internal.wrap;


import com.mioto.pms.netty.tcp.protocol.CodecConfig;
import com.mioto.pms.netty.tcp.protocol.Packet;
import com.mioto.pms.netty.tcp.protocol.internal.ProtocalTemplate;
import com.mioto.pms.netty.tcp.protocol.internal.packetSegment.PacketSegmentContext;

/**
 * Created by PETER on 2015/3/24.
 */
public abstract class Wrapper {
    protected Wrapper next;
    abstract void encode(Packet in, PacketSegmentContext packetSegmentContext,
                         ProtocalTemplate protocalTemplate, CodecConfig codecConfig) throws Exception;

    public void setNext(Wrapper next) {
        this.next = next;
    }
}
