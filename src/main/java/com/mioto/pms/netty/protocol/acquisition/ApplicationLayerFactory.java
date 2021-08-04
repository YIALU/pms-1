package com.mioto.pms.netty.protocol.acquisition;

import java.util.HashMap;
import java.util.Map;

/**
 * @author admin
 * @date 2021-06-01 16:18
 */
public class ApplicationLayerFactory {

    private static Map<String,ApplicationLayer> layerMap = new HashMap<>(4);

    static {
        //链路接口检测
        layerMap.put("02",new LidaLayer());
    }

    public static ApplicationLayer getInstance(String data){
        String afn = data.substring(AcquisitionProtocolEnum.AFN.getStart(),AcquisitionProtocolEnum.AFN.getEnd());
        ApplicationLayer applicationLayer = layerMap.get(afn);
        applicationLayer.setAfn(afn);
        applicationLayer.setSeq(data.substring(AcquisitionProtocolEnum.SEQ.getStart(),AcquisitionProtocolEnum.SEQ.getEnd()));
        applicationLayer.parse(data);
        return applicationLayer;
    }
}
