package com.mioto.pms.netty.protocol.acquisition;

/**
 * @author admin
 * @date 2021-06-01 11:33
 */
public class AcquisitionProtocolHelper {

    public static AcquisitionProtocol parse(String data){
        AcquisitionProtocol acquisitionProtocol = new AcquisitionProtocol();
        acquisitionProtocol.setLen(data.substring(AcquisitionProtocolEnum.LEN.getStart(),AcquisitionProtocolEnum.LEN.getEnd()));
        acquisitionProtocol.setControlDomain(data.substring(AcquisitionProtocolEnum.CONTROL_DOMAIN.getStart(),AcquisitionProtocolEnum.CONTROL_DOMAIN.getEnd()));
        acquisitionProtocol.setAddressDomain(data.substring(AcquisitionProtocolEnum.ADDRESS_DOMAIN.getStart(),AcquisitionProtocolEnum.ADDRESS_DOMAIN.getEnd()));
        ApplicationLayer applicationLayer = ApplicationLayerFactory.getInstance(data);
        acquisitionProtocol.setApplicationLayer(applicationLayer);
        return acquisitionProtocol;
    }


}
