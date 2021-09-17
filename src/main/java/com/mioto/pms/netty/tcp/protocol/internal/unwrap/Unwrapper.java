package com.mioto.pms.netty.tcp.protocol.internal.unwrap;

import com.mioto.pms.netty.tcp.protocol.CodecConfig;
import com.mioto.pms.netty.tcp.protocol.internal.ProtocalTemplate;
import com.mioto.pms.netty.tcp.protocol.internal.packetSegment.PacketSegmentContext;

import java.nio.ByteBuffer;

/**
 * Created by PETER on 2015/3/25.
 */
public abstract class Unwrapper {
    protected Unwrapper next;
    abstract  void decode(ByteBuffer in, PacketSegmentContext packetSegmentContext,
                          ProtocalTemplate protocalTemplate, CodecConfig codecConfig) throws Exception;

    void setNext(Unwrapper unwrapper){
        next=unwrapper;
    }

}
