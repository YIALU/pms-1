package com.mioto.pms.quartz;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.mioto.pms.component.bill.AutoMeterReadBill;
import com.mioto.pms.module.device.model.Device;
import com.mioto.pms.module.device.service.IDeviceService;
import com.mioto.pms.netty.ChannelUtil;
import com.mioto.pms.netty.tcp.TcpHelper;
import com.mioto.pms.utils.SpringBeanUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.List;


/**
 * 每月自动抄表定时任务 - 发送抄表指令到设备
 * @author admin
 * @date 2021-07-16 18:15
 */
@Slf4j
public class MeterReadingMonthTask extends AbstractMeterReadingTask{

    private String roomId;

    public MeterReadingMonthTask(String roomId){
        this.roomId = roomId;
    }

    @Override
    protected void sendMeterReadingCmd() {
        if (StrUtil.isNotEmpty(roomId)) {
            List<Device> deviceList = SpringBeanUtil.getBean(IDeviceService.class).findByRoomId(roomId);
            int result = 0;
            if (CollUtil.isNotEmpty(deviceList)){
                for (Device device : deviceList) {
                    if (StrUtil.isNotEmpty(device.getFocus()) && ChannelUtil.containsKey(device.getFocus())){
                        TcpHelper.meterReading(device.getLine(),device.getFocus());
                        log.info("执行每月自动抄表任务,集中器地址 - {},序号 - {}",device.getFocus(),device.getLine());
                        result++;
                    }
                }
                if (result > 0) {
                    genBill(roomId);
                }
            }
        }
    }

    public void genBill(String roomId){
        int minute = 3;
        log.info("添加动态定时任务,抄表指令发出后 - {} 分钟,生成账单信息" , minute);
        Date date = new Date();
        DateTime dateTime = DateUtil.offsetMinute(date,minute);
        String dateStr = DateUtil.format(dateTime,"mm:ss");
        QuartzManager quartzManager = new QuartzManager();
        String pattern = quartzManager.createFixQuartz(dateStr);
        final String schedulerId = IdUtil.simpleUUID();
        quartzManager.startTask(schedulerId,pattern,()->{
            SpringBeanUtil.getBean(AutoMeterReadBill.class).create(roomId);
            quartzManager.deleteDynamicTask(schedulerId);
        });
    }
}
