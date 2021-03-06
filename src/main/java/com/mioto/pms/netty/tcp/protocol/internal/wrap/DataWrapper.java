package com.mioto.pms.netty.tcp.protocol.internal.wrap;


import com.mioto.pms.netty.tcp.protocol.CodecConfig;
import com.mioto.pms.netty.tcp.protocol.Packet;
import com.mioto.pms.netty.tcp.protocol.exception.DecodingException;
import com.mioto.pms.netty.tcp.protocol.exception.EncodingException;
import com.mioto.pms.netty.tcp.protocol.exception.InvalidConfigException;
import com.mioto.pms.netty.tcp.protocol.internal.ConfigParse.*;
import com.mioto.pms.netty.tcp.protocol.internal.ProtocalTemplate;
import com.mioto.pms.netty.tcp.protocol.internal.fieldGroupFormater.FormatterImpl;
import com.mioto.pms.netty.tcp.protocol.internal.fieldGroupFormater.IFormatter;
import com.mioto.pms.netty.tcp.protocol.internal.fieldType.Da;
import com.mioto.pms.netty.tcp.protocol.internal.fieldType.Dt;
import com.mioto.pms.netty.tcp.protocol.internal.fieldType.FieldTypeContext;
import com.mioto.pms.netty.tcp.protocol.internal.fieldType.IFieldType;
import com.mioto.pms.netty.tcp.protocol.internal.packetSegment.Data;
import com.mioto.pms.netty.tcp.protocol.internal.packetSegment.PacketSegmentContext;
import com.mioto.pms.netty.tcp.protocol.internal.packetSegment.SegmentEnum;
import com.mioto.pms.netty.tcp.protocol.internal.validator.IValidator;
import com.mioto.pms.netty.tcp.protocol.internal.validator.ValidatorContext;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by PETER on 2015/3/25.
 */
public class DataWrapper extends Wrapper implements Constants {
    private static final String MULTIPLECOMMAND="multipleCommand";
    private Da da=new Da();
    private Dt dt=new Dt();
    private FieldTypeParam dadtParam=new FieldTypeParam(2);

    private FieldTypeContext fieldTypeContext=new FieldTypeContext();
    private ValidatorContext validatorContext=new ValidatorContext();
    private IFormatter formatter=new FormatterImpl();
    private Map<String,EncodeFieldGroup> encodeFieldGroupMap=new HashMap<>();

    public DataWrapper(){
        init();
    }

    private void init(){
        encodeFieldGroupMap.put(LIST, new EncodeFieldGroup() {
            /**
             * ??????list??????group
             * @param fieldGroup
             * @param dataContentMap
             * @param buffer
             * @param protocalTemplate
             * @throws Exception
             */
            @Override
            public void execute(FieldGroup fieldGroup, Map<String,Object> dataContentMap,
                                List<byte[]> buffer, ProtocalTemplate protocalTemplate) throws Exception{
                ExpressionParser parser = new SpelExpressionParser();
                EvaluationContext context = new StandardEvaluationContext();
                context.setVariable("data", dataContentMap);
                int numRef;
                try {
                    numRef = parser.parseExpression(fieldGroup.getRefNum()).
                            getValue(context, Integer.class);
                }catch (Exception e){
                    e.printStackTrace();
                    throw new DecodingException(1111,"list??????????????????:"+fieldGroup.getRefNum());
                }
                List<Object> list = (List) dataContentMap.get(LIST);
                for (int i = 0; i < numRef; i++) {
                    Object manyIndex = list.get(i);
                    if(fieldGroup.getFormater()!=null && manyIndex instanceof String){
                        Map manyMapIndex=formatter.division(fieldGroup.getFormater(),manyIndex.toString());
                        encodeData(fieldGroup, manyMapIndex,buffer,protocalTemplate);
                    }else if(manyIndex instanceof Map){
                        encodeData(fieldGroup, (Map)manyIndex,buffer,protocalTemplate);
                    }else{
                        throw new EncodingException(1111,"??????????????????"+manyIndex);
                    }
                }
            }
        });


        encodeFieldGroupMap.put(FIELDGROUP, new EncodeFieldGroup() {
            /**
             * ??????????????????group
             * @param fieldGroup
             * @param dataContentMap
             * @param buffer
             * @param protocalTemplate
             * @throws Exception
             */
            @Override
            public void execute(FieldGroup fieldGroup, Map<String, Object> dataContentMap, List<byte[]> buffer, ProtocalTemplate protocalTemplate) throws Exception {
                Object value=dataContentMap.get(fieldGroup.getName());
                if(fieldGroup.getFormater()!=null && value instanceof String){
                    Map valueMap=formatter.division(fieldGroup.getFormater(),value.toString());
                    encodeData(fieldGroup, valueMap , buffer,protocalTemplate);
                }else if(value instanceof Map){
                    encodeData(fieldGroup, (Map)value , buffer,protocalTemplate);
                }else{
                    throw new EncodingException(1111,"??????????????????"+value);
                }
            }
        });

        encodeFieldGroupMap.put(REFFIELDGROUP, new EncodeFieldGroup() {
            /**
             * ??????????????????group
             * @param fieldGroup
             * @param dataContentMap
             * @param buffer
             * @param protocalTemplate
             * @throws Exception
             */
            @Override
            public void execute(FieldGroup fieldGroup, Map<String, Object> dataContentMap, List<byte[]> buffer, ProtocalTemplate protocalTemplate) throws Exception {
                String refType = fieldGroup.getRefType();
                FieldGroup refGroup = protocalTemplate.getFieldGroupMap().get(refType);
                if (refGroup == null) {
                    throw new InvalidConfigException(1111, "?????????refGroup:" + refType);
                }
                Object value = dataContentMap.get(fieldGroup.getName());

                if (refGroup.getFormater() != null) {
                    if (value instanceof String) {
                        Map valueMap = formatter.division(refGroup.getFormater(), value.toString());
                        encodeData(refGroup, valueMap, buffer, protocalTemplate);
                    }else if(value instanceof Map){
//                    	Map valueMap = formatter.division(refGroup.getFormater(), value.toString());
                        encodeData(refGroup, (Map)value, buffer, protocalTemplate);
                    } 
                    else {
                        throw new EncodingException(1111, "??????????????????" + value);
                    }
                } else {
                    encodeData(refGroup, dataContentMap, buffer, protocalTemplate);
                }
            }
        });
    }


    /**
     * ???????????????
     * @param in
     * @param packetSegmentContext
     * @param protocalTemplate
     * @param codecConfig
     * @throws Exception
     */
    @Override
    public void encode(Packet in, PacketSegmentContext packetSegmentContext, ProtocalTemplate protocalTemplate, CodecConfig codecConfig) throws Exception {
        Data dataSeg=(Data)packetSegmentContext.getSegment(SegmentEnum.data);
        Map<String,Object> dataContentMap=in.getData();
        List<byte[]> buffer=dataSeg.getBuffer();
        if(in.getCommand().equals(MULTIPLECOMMAND)){
            Iterator commandIter=in.getData().keySet().iterator();
            while (commandIter.hasNext()){
                String command=commandIter.next().toString();
                FieldGroup dataGroup=protocalTemplate.getDataMapByName().get(command);
                dataSeg.setFn(dataGroup.getFn());
                buffer.add(da.encode(dataSeg.getPn(), dadtParam));
                buffer.add(dt.encode(dataSeg.getFn(),dadtParam));
                if(dataGroup==null){
                    throw new EncodingException(1111,"??????????????????:"+in.getCommand());
                }
                encodeData(dataGroup, (Map)dataContentMap.get(command), buffer, protocalTemplate);
            }


        }else{
            buffer.add(da.encode(dataSeg.getPn(),dadtParam));
            buffer.add(dt.encode(dataSeg.getFn(),dadtParam));
            dataSeg.setData(in.getData());
            FieldGroup dataGroup=protocalTemplate.getDataMapByName().get(in.getCommand());
            if(dataGroup==null){
                throw new EncodingException(1111,"??????????????????:"+in.getCommand());
            }
            encodeData(dataGroup, dataContentMap, buffer, protocalTemplate);
        }

        if(next!=null){
            next.encode(in,packetSegmentContext,protocalTemplate,codecConfig);
        }

    }


    /**
     * ???????????????
     * @param dataGroup
     * @param dataContentMap
     * @param buffer
     * @param protocalTemplate
     * @throws Exception
     */
    private void encodeData(FieldGroup dataGroup,Map<String,Object> dataContentMap,
                            List<byte[]> buffer,ProtocalTemplate protocalTemplate) throws Exception{
        //??????????????????fieldgroup ?????????
        if (dataGroup.getDirection() == 2) {
            return;
        }
        Iterator dataIt=dataGroup.getChildNodes().entrySet().iterator();

        while (dataIt.hasNext()) {
            Map.Entry entry = (Map.Entry) dataIt.next();
            IFieldNode IFieldNode = (IFieldNode) entry.getValue();
            String name = (String) entry.getKey();
            if (IFieldNode instanceof Field) {
                Field field = (Field) IFieldNode;
                if (field.getDirection() == 2) {
                    continue;
                }
                encodeField(name, field, dataContentMap, buffer);
            }
            //??????group???????????????
            if (IFieldNode instanceof FieldGroup) {
                FieldGroup fieldGroup = (FieldGroup) IFieldNode;
                if (fieldGroup.getDirection() == 2) {
                    continue;
                }
                EncodeFieldGroup encodeFieldGroup=
                        encodeFieldGroupMap.get(fieldGroup.getType());
                if(encodeFieldGroup!=null){
                    encodeFieldGroup.execute(fieldGroup,dataContentMap,buffer,protocalTemplate);
                }

            }
        }
    }


    /**
     * ????????????
     * @param name
     * @param field
     * @param dataContentMap
     * @param buffer
     * @throws Exception
     */
    private void encodeField(String name,Field field,Map<String,Object> dataContentMap,List<byte[]> buffer) throws Exception{
        IFieldType fieldType =fieldTypeContext.get(field.getFieldType());
        if(fieldType==null){
            throw new InvalidConfigException(1111,"?????????fieldType:"+field.getName());
        }
        Object value=dataContentMap.get(name);
        if(value==null){
            value=field.getDefaultValue();
            if(value==null){
                throw new EncodingException(1111,name+"???????????????");
            }
        }
        if(field.getValidators()!=null){
            for(Validator v:field.getValidators()){
                IValidator validator=validatorContext.get(v.getName());
                if(!validator.check(value,v.getTarget())){
                    throw new EncodingException(111,name+"????????? <"+value+">??????");
                }
            }
        }
        buffer.add(fieldType.encode(value, field.getFieldTypeParam()));
    }


    /**
     * ????????????
     */
    private interface EncodeFieldGroup{
        void execute(FieldGroup fieldGroup,Map<String,Object> dataContentMap,
                     List<byte[]> buffer,ProtocalTemplate protocalTemplate) throws Exception;
    }
}
