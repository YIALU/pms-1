package com.mioto.pms.netty.tcp.protocol.internal.unwrap;

import com.mioto.pms.netty.tcp.protocol.CodecConfig;
import com.mioto.pms.netty.tcp.protocol.exception.DecodingException;
import com.mioto.pms.netty.tcp.protocol.exception.InvalidConfigException;
import com.mioto.pms.netty.tcp.protocol.internal.ConfigParse.*;
import com.mioto.pms.netty.tcp.protocol.internal.ProtocalTemplate;
import com.mioto.pms.netty.tcp.protocol.internal.fieldGroupFormater.FormatterImpl;
import com.mioto.pms.netty.tcp.protocol.internal.fieldGroupFormater.IFormatter;
import com.mioto.pms.netty.tcp.protocol.internal.fieldType.Da;
import com.mioto.pms.netty.tcp.protocol.internal.fieldType.Dt;
import com.mioto.pms.netty.tcp.protocol.internal.fieldType.FieldTypeContext;
import com.mioto.pms.netty.tcp.protocol.internal.fieldType.IFieldType;
import com.mioto.pms.netty.tcp.protocol.internal.packetSegment.Control;
import com.mioto.pms.netty.tcp.protocol.internal.packetSegment.Data;
import com.mioto.pms.netty.tcp.protocol.internal.packetSegment.PacketSegmentContext;
import com.mioto.pms.netty.tcp.protocol.internal.packetSegment.SegmentEnum;

import lombok.extern.slf4j.Slf4j;

import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.nio.ByteBuffer;
import java.util.*;

/**
 * Created by PETER on 2015/3/26.
 */
@Slf4j
public class DataUnwrapper extends Unwrapper implements Constants{
    private Da da=new Da();
    private Dt dt=new Dt();
    private FieldTypeParam dadtParam=new FieldTypeParam(2);

    private FieldTypeContext fieldTypeContext=new FieldTypeContext();
    private IFormatter formatter=new FormatterImpl();
    private Map<String,DecodeFieldGroup> decodeFieldGroupMap=new HashMap<>();
    private static final int AFN_EVENT=14;

    public DataUnwrapper(){
        init();
    }

    private void init(){
        decodeFieldGroupMap.put(LIST, new DecodeFieldGroup() {
            @Override
            public void execute(FieldGroup fieldGroup, Map<String, Object> dataContent,
                                ByteBuffer byteBuffer, ProtocalTemplate protocalTemplate) throws Exception {
                ExpressionParser parser = new SpelExpressionParser();
                EvaluationContext context = new StandardEvaluationContext();
                context.setVariable("data", dataContent);
                int numRef;
                try {
                    numRef = parser.parseExpression(fieldGroup.getRefNum()).
                            getValue(context, Integer.class);
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new DecodingException(1111, "list??????????????????:" + fieldGroup.getRefNum());
                }
                List<Object> listContent = new ArrayList<>();
                for (int i = 0; i < numRef; i++) {
                    Map<String, Object> many = decodeData(fieldGroup, byteBuffer, protocalTemplate);
                    //format??????
                    if (fieldGroup.getFormater() != null) {
                        listContent.add(formatter.union(fieldGroup.getFormater(), many));
                    } else {
                        listContent.add(many);
                    }

                }
                dataContent.put(LIST, listContent);
            }
        });

        decodeFieldGroupMap.put(FIELDGROUP, new DecodeFieldGroup() {
            @Override
            public void execute(FieldGroup fieldGroup,Map<String,Object> dataContent,
                                ByteBuffer byteBuffer,ProtocalTemplate protocalTemplate) throws Exception {
                Map fieldGroupContent=decodeData(fieldGroup,byteBuffer,protocalTemplate);
                //format??????
                if(fieldGroup.getFormater()!=null){
                    dataContent.put(fieldGroup.getName(),
                            formatter.union(fieldGroup.getFormater(),fieldGroupContent));
                }else {
                    dataContent.put(fieldGroup.getName(),fieldGroupContent);
                }
            }
        });

        decodeFieldGroupMap.put(REFFIELDGROUP, new DecodeFieldGroup() {
            @Override
            public void execute(FieldGroup fieldGroup, Map<String, Object> dataContent,
                                ByteBuffer byteBuffer, ProtocalTemplate protocalTemplate) throws Exception {
                String groupType = fieldGroup.getRefType();
                FieldGroup refGroup = protocalTemplate.getFieldGroupMap().get(groupType);
                Map fieldGroupContent = decodeData(refGroup, byteBuffer, protocalTemplate);
                //format??????
                if (refGroup.getFormater() != null) {
                    dataContent.put(fieldGroup.getName(),
                            formatter.union(refGroup.getFormater(), fieldGroupContent));
                } else {
                    dataContent.put(fieldGroup.getName(), fieldGroupContent);
                }
            }
        });
    }

    @Override
    public void decode(ByteBuffer in, PacketSegmentContext packetSegmentContext,
                       ProtocalTemplate protocalTemplate, CodecConfig codecConfig) throws Exception{
        Data dataSeg=(Data)packetSegmentContext.getSegment(SegmentEnum.data);
        Control control=(Control)packetSegmentContext.getSegment(SegmentEnum.control);
        Map<String,Object> dataContentMap=new HashMap<>();
        String command=decodeDataSeg(in,protocalTemplate,dataSeg,control);
        //????????????????????????
        int tailLeft=4;
        if(control.getFcbOrAcd()==1){
            tailLeft+=2;
        }
        if(control.getTpV()==1){
            tailLeft+=6;
        }
        while(in.limit()-in.position()>=tailLeft){
            //???????????????????????????????????????                     ??????????????????
            if(control.getAfn()==0 || control.getAfn() == 12){
                break;
            }
            //?????????????????????
            dataContentMap.put(command,dataSeg.getData());
            dataSeg.reset();
            command=decodeDataSeg(in,protocalTemplate,dataSeg,control);
        }

        if(!dataContentMap.isEmpty()){
            //???????????????????????????
            dataContentMap.put(command,dataSeg.getData());
            dataSeg.setCommand("multipleCommand");
            dataSeg.setData(dataContentMap);
        }

        if(next!=null){
            next.decode(in,packetSegmentContext,protocalTemplate,codecConfig);
        }

    }

    private String decodeDataSeg(ByteBuffer in, ProtocalTemplate protocalTemplate,
                               Data dataSeg,Control control) throws Exception{
        Map<String,Object> dataContentMap;
        dataSeg.setPn(da.decode(in,dadtParam));
        dataSeg.setFn(dt.decode(in,dadtParam));

        String path=String.format("afn%02xhf%s",control.getAfn(),dataSeg.getFn());
        FieldGroup dataGroup=protocalTemplate.getDataMap().get(path);
       /* if(path.equals("afn10hf1")){
        	throw new DecodingException(1111, "????????????");
        }*/
        if(dataGroup==null){
//        	if(dataSeg.getFn() == -136){
//        		
//        	}
//        	log.error("receive unknown message - {}",String.valueOf(HexUtil.encodeHex(HexString.bytebuffer2ByteArray(in))));
            throw new DecodingException(1111,String.format("????????????????????????,afn=%s,fn=%s",
                    control.getAfn(),dataSeg.getFn()));
        }else{
            dataSeg.setCommand(dataGroup.getName());

            //???????????????????????????????????????????????????
            if(dataGroup.getDirection()!=1) {
                if (AFN_EVENT == control.getAfn()) {
                    dataContentMap = decodeEvent(dataGroup, in, protocalTemplate);
                } else {
                    dataContentMap = decodeData(dataGroup, in, protocalTemplate);
                }
            }else{
                dataContentMap=new HashMap<>();
            }
            dataSeg.setData(dataContentMap);
        }

        return dataGroup.getName();
    }

    private Map decodeData(FieldGroup dataGroup,final ByteBuffer byteBuffer,ProtocalTemplate protocalTemplate) throws Exception{
        Map<String,Object> dataContent=new LinkedHashMap<>();
        Iterator dataIt=dataGroup.getChildNodes().entrySet().iterator();
        while (dataIt.hasNext()){
            Map.Entry entry=(Map.Entry)dataIt.next();
            IFieldNode fieldNode =(IFieldNode)entry.getValue();
            String name= (String) entry.getKey();
            if(fieldNode instanceof Field){
                Field field=(Field) fieldNode;
                if(field.getDirection()==1){
                    continue;
                }
                decodeField(name,field,dataContent,byteBuffer);
            }
            //??????group???????????????
            if(fieldNode instanceof FieldGroup){
                FieldGroup fieldGroup=(FieldGroup) fieldNode;
                if(fieldGroup.getDirection()==1){
                    continue;
                }

                DecodeFieldGroup decodeFieldGroup=decodeFieldGroupMap.get(fieldGroup.getType());
                if(decodeFieldGroup!=null){
                    decodeFieldGroup.execute(fieldGroup,dataContent,byteBuffer,protocalTemplate);
                }

            }
        }
        return dataContent;
    }

    private void decodeField(String name,Field field,Map Content,final ByteBuffer byteBuffer) throws Exception{
        IFieldType fieldType =fieldTypeContext.get(field.getFieldType());
        if(fieldType==null){
            throw new InvalidConfigException(1111,"?????????fieldType:"+field.getName());
        }
        Object value= fieldType.decode(byteBuffer, field.getFieldTypeParam());
        if(!field.isHidden()){
            Content.put(name,value);
        }
    }

    private Map decodeEvent(FieldGroup dataGroup,ByteBuffer byteBuffer,
                            ProtocalTemplate protocalTemplate) throws Exception{
        Map<String,Object> event=new HashMap<>();
        //ec1
        byteBuffer.get();
        //ec2
        byteBuffer.get();
        int pm=byteBuffer.get();
        int pn=byteBuffer.get();
        //????????????
        int eventNum=0;
        if(pn>=pm){
            eventNum=pn-pm;
        }else {
            eventNum=256+pn-pm;
        }
        for(int i=0;i<eventNum;i++){
            String id=String.valueOf(byteBuffer.get()&0xff);
            FieldGroup eventGroup=(FieldGroup)protocalTemplate.getEventMap().get(id);
            if(eventGroup==null){
                throw new DecodingException(1111,"?????????????????????,?????????:"+id);
            }
            Map<String,Object> eventMap=decodeData(eventGroup,byteBuffer,protocalTemplate);
            //?????????????????????????????????????????????????????????
            eventMap.remove(LENGTH);
            event.put(eventGroup.getName(),eventMap);
        }
        return event;
    }


    private interface DecodeFieldGroup{
        void execute(FieldGroup fieldGroup,Map<String,Object> dataContent,
                     ByteBuffer byteBuffer,ProtocalTemplate protocalTemplate) throws Exception;
    }


}
