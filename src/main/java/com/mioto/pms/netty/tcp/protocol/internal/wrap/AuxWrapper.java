package com.mioto.pms.netty.tcp.protocol.internal.wrap;


import com.mioto.pms.netty.tcp.protocol.CodecConfig;
import com.mioto.pms.netty.tcp.protocol.Packet;
import com.mioto.pms.netty.tcp.protocol.exception.Preconditions;
import com.mioto.pms.netty.tcp.protocol.internal.ConfigParse.FieldTypeParam;
import com.mioto.pms.netty.tcp.protocol.internal.ProtocalTemplate;
import com.mioto.pms.netty.tcp.protocol.internal.fieldType.BcdUnsigned;
import com.mioto.pms.netty.tcp.protocol.internal.fieldType.HexString;
import com.mioto.pms.netty.tcp.protocol.internal.packetSegment.Auxiliary;
import com.mioto.pms.netty.tcp.protocol.internal.packetSegment.Control;
import com.mioto.pms.netty.tcp.protocol.internal.packetSegment.PacketSegmentContext;
import com.mioto.pms.netty.tcp.protocol.internal.packetSegment.SegmentEnum;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by PETER on 2015/3/24.
 */
public class AuxWrapper extends Wrapper{
    private HexString hexString=new HexString();
    private BcdUnsigned bcdUnsigned=new BcdUnsigned();
    private FieldTypeParam passwordParam=new FieldTypeParam(16);
    private FieldTypeParam timeField=new FieldTypeParam(1);
    private Calendar calendar=new GregorianCalendar();

    @Override
    public void encode(Packet in, PacketSegmentContext packetSegmentContext, ProtocalTemplate protocalTemplate, CodecConfig codecConfig) throws Exception {
        Control control=(Control)packetSegmentContext.getSegment(SegmentEnum.control);
        Auxiliary auxiliary=(Auxiliary)packetSegmentContext.getSegment(SegmentEnum.auxiliary);
        List<byte[]> buffer=auxiliary.getBuffer();
        //密码 编码
        auxiliary.setPassword(codecConfig.getPassword());
        int afn=control.getAfn();
        if(afn==0x01 || afn==0x04 || afn==0x05 || afn==0x0F || afn==0x1c || afn== 0x10){
            Preconditions.checkNotNull(auxiliary.getPassword(),1111,"密码值未设置");
            //05规约，密码2字节
            if(codecConfig.getProtocalVersion()==1){
                passwordParam.setLength(2);
            }else{
                passwordParam.setLength(16);
            }
            buffer.add(hexString.encode(codecConfig.getPassword(), passwordParam));
        }

        if(codecConfig.isHaveTp()){
            calendar.setTimeInMillis(System.currentTimeMillis());
            buffer.add(new byte[]{(byte)auxiliary.getPfc()});
            buffer.add(bcdUnsigned.encode(calendar.get(Calendar.SECOND),timeField));
            buffer.add(bcdUnsigned.encode(calendar.get(Calendar.MINUTE),timeField));
            buffer.add(bcdUnsigned.encode(calendar.get(Calendar.HOUR_OF_DAY),timeField));
            buffer.add(bcdUnsigned.encode(calendar.get(Calendar.DATE),timeField));
            buffer.add(new byte[]{(byte)auxiliary.getTimeout()});
        }

        if(next!=null){
            next.encode(in,packetSegmentContext,protocalTemplate,codecConfig);
        }
    }

}
