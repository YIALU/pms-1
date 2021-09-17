package com.mioto.pms.netty.tcp.protocol.internal.wrap;


import com.mioto.pms.netty.tcp.protocol.CodecConfig;
import com.mioto.pms.netty.tcp.protocol.Packet;
import com.mioto.pms.netty.tcp.protocol.internal.ProtocalTemplate;
import com.mioto.pms.netty.tcp.protocol.internal.packetSegment.PacketSegmentContext;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PETER on 2015/3/24.
 */
public class WrapperChain {
    List<Wrapper> wrappers=new ArrayList<>();

    public void add(Wrapper wrapper){
        if(wrappers.size()>0){
            Wrapper pre=wrappers.get(wrappers.size()-1);
            pre.setNext(wrapper);
        }
        wrappers.add(wrapper);
    }

    public void encode(Packet in, PacketSegmentContext packetSegmentContext,
                       ProtocalTemplate protocalTemplate, CodecConfig codecConfig) throws Exception{
        if(wrappers.size()>0){
            wrappers.get(0).encode(in,packetSegmentContext,protocalTemplate,codecConfig);
        }
    }
}
