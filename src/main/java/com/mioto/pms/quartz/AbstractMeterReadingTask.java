package com.mioto.pms.quartz;

import cn.hutool.cron.task.Task;

/**
 * 自动抄表抽象类
 * @author admin
 * @date 2021-08-31 14:30
 */
public abstract class AbstractMeterReadingTask implements Task {

    @Override
    public void execute() {
        sendMeterReadingCmd();
    }

    /**
     * 发送抄表指令
     */
    protected abstract void sendMeterReadingCmd();
}
