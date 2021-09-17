package com.mioto.pms.netty.tcp.protocol.internal.unwrap;

import com.mioto.pms.netty.tcp.protocol.CodecConfig;
import com.mioto.pms.netty.tcp.protocol.internal.ProtocalTemplate;
import com.mioto.pms.netty.tcp.protocol.internal.packetSegment.PacketSegmentContext;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by PETER on 2015/3/25.
 */
public class UnwrapperChain{
    private List<Unwrapper> unwrappers=new ArrayList<>();

    public void add(Unwrapper unwrapper){
        if(unwrappers.size()>0){
            Unwrapper pre=unwrappers.get(unwrappers.size()-1);
            pre.setNext(unwrapper);
        }
        unwrappers.add(unwrapper);
    }

    public void decode(ByteBuffer in, PacketSegmentContext packetSegmentContext, ProtocalTemplate protocalTemplate, CodecConfig codecConfig) throws Exception {
        if(unwrappers.size()>0){
            unwrappers.get(0).decode(in,packetSegmentContext,protocalTemplate,codecConfig);
        }
    }
}
