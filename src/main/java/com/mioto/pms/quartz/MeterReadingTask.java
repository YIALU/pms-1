package com.mioto.pms.quartz;

import cn.hutool.core.util.StrUtil;
import cn.hutool.cron.task.Task;
import com.mioto.pms.module.cost.service.ICostInfoService;
import com.mioto.pms.utils.SpringBeanUtil;
import lombok.extern.slf4j.Slf4j;


/**
 * 自动抄表定时任务 - 发送抄表指令到设备
 * @author admin
 * @date 2021-07-16 18:15
 */
@Slf4j
public class MeterReadingTask implements Task {

    private String roomId;

    public MeterReadingTask(String roomId){
        this.roomId = roomId;
    }

    @Override
    public void execute() {
        log.info("发送抄表指令");
        //发送指令成功,添加收费信息及明细
        insertCostInfo();
    }


    private void insertCostInfo(){
        if (StrUtil.isNotEmpty(roomId)){
            SpringBeanUtil.getBean(ICostInfoService.class).insertDetail(roomId,false);
        }
    }
}
