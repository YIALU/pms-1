package com.mioto.pms.quartz;

import cn.hutool.core.collection.CollUtil;
import com.mioto.pms.module.device.model.Device;
import com.mioto.pms.module.device.service.IDeviceService;
import com.mioto.pms.netty.ChannelUtil;
import com.mioto.pms.netty.tcp.TcpHelper;
import com.mioto.pms.utils.SpringBeanUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Set;

/**
 * 每日抄表任务
 * @author admin
 * @date 2021-08-31 15:29
 */
@Slf4j
public class MeterReadingDayTask extends AbstractMeterReadingTask{

    @Override
    protected void sendMeterReadingCmd() {
        //仅获取在线的设备
        Set<String> set = ChannelUtil.getTerminalAddress();
        if (CollUtil.isNotEmpty(set)){
            List<Device> deviceList = SpringBeanUtil.getBean(IDeviceService.class).findByFocus(set);
            for (Device device : deviceList) {
                TcpHelper.meterReading(device.getLine(),device.getFocus());
                log.info("执行每日自动抄表任务,集中器地址 - {},序号 - {}",device.getFocus(),device.getLine());
            }
        }
    }
}
