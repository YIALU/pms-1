package com.mioto.pms.netty.tcp.protocol.internal.wrap;

import com.mioto.pms.netty.tcp.protocol.CodecConfig;
import com.mioto.pms.netty.tcp.protocol.Packet;
import com.mioto.pms.netty.tcp.protocol.internal.ProtocalTemplate;
import com.mioto.pms.netty.tcp.protocol.internal.packetSegment.Control;
import com.mioto.pms.netty.tcp.protocol.internal.packetSegment.Data;
import com.mioto.pms.netty.tcp.protocol.internal.packetSegment.PacketSegmentContext;
import com.mioto.pms.netty.tcp.protocol.internal.packetSegment.SegmentEnum;

/**
 * Created by PETER on 2015/5/5.
 */
public class CipherWrapper extends Wrapper{

    @Override
    void encode(Packet in, PacketSegmentContext packetSegmentContext, ProtocalTemplate protocalTemplate, CodecConfig codecConfig) throws Exception {
        Control control=(Control)packetSegmentContext.getSegment(SegmentEnum.control);
        Data dataSeg=(Data)packetSegmentContext.getSegment(SegmentEnum.data);
        int afn=control.getAfn();

        if(afn==0x04 || afn==0x05 || afn == 0x1c){
            /*List<byte[]> body=dataSeg.getBuffer();
            //加密
            List<byte[]> crypted=body;
            dataSeg.getBuffer().clear();
            dataSeg.getBuffer().addAll(crypted);*/
        }
        if(next!=null){
            next.encode(in,packetSegmentContext,protocalTemplate,codecConfig);
        }
    }
}
