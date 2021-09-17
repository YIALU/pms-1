package com.mioto.pms.netty.tcp.protocol.resolver;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.mioto.pms.module.device.DeviceTypeEnum;
import com.mioto.pms.module.device.model.Device;
import com.mioto.pms.module.device.service.IDeviceService;
import com.mioto.pms.module.meter.model.MeterData;
import com.mioto.pms.module.meter.service.MeterReadingService;
import com.mioto.pms.netty.tcp.protocol.Packet;
import com.mioto.pms.netty.tcp.protocol.model.CurrentPositiveActiveData;
import com.mioto.pms.utils.SpringBeanUtil;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

/**
 * @author admin
 * @date 2021-07-05 15:46
 */
@Slf4j
public class CurrentPositiveActiveEnergy extends AbstractMessageResolver {

    @Override
    public void resolve(ChannelHandlerContext ctx, Packet packet) throws Exception {
        log.info("Meter reading returns data object - {}", packet);
        Device device = SpringBeanUtil.getBean(IDeviceService.class).findByLineAndfocus(packet.getLine(), packet.getTerminalAddress());
        double totalElectricEnergy = NumberUtil.parseNumber(packet.getData().get("totalElectricEnergy").toString()).doubleValue() ;
        //如果是水表，去除小数
        if (StrUtil.equals(device.getDeviceTypeId(), DeviceTypeEnum.WATER_METER.getId())){
            totalElectricEnergy = NumberUtil.roundDown(totalElectricEnergy,0).doubleValue();
        }
        CurrentPositiveActiveData packetData = new CurrentPositiveActiveData();
        packetData.setTotalElectricEnergy(totalElectricEnergy);
        packetData.setDeviceType(device.getDeviceTypeId());
        reply2Websocket(ctx,packetData);

        save2Db(device.getId(),totalElectricEnergy,device.getDeviceTypeId());
    }

    private void save2Db(int deviceId,double data,String deviceType){
        MeterReadingService meterReadingService = SpringBeanUtil.getBean(MeterReadingService.class);
        MeterData md = meterReadingService.findLastData(deviceType,deviceId);
        MeterData meterData = new MeterData();
        meterData.setValue(data);
        meterData.setDeviceId(deviceId);
        Date currentDate = new Date();
        meterData.setCreateDate(currentDate);
        meterData.setDeviceType(deviceType);
        if (ObjectUtil.isNotEmpty(md)){
            double usageAmount = NumberUtil.sub(data, md.getValue());
            if (DateUtil.isSameDay(md.getCreateDate(),currentDate)){
                meterData.setId(md.getId());
                meterData.setUsageAmount(NumberUtil.add(usageAmount, md.getUsageAmount()));
            }else {
                meterData.setUsageAmount(usageAmount);
            }
        }
        meterReadingService.saveMeterData(meterData);
        log.info("save meter reading data to database - {}",meterData);
    }

}
