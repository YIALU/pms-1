package com.mioto.pms.component;

import cn.hutool.cron.task.Task;
import com.mioto.pms.quartz.OverdueNotifyTask;

/**
 * @author admin
 * @date 2021-09-04 16:02
 */
public enum SchedulerType {
    /**
     * 催收任务
     */
    OVERDUE_NOTIFY("overdueNotify",new OverdueNotifyTask());



    private String type;

    private Task task;

    public String getType() {
        return type;
    }

    public Task getTask() {
        return task;
    }

    SchedulerType(String type, Task task) {
        this.type = type;
        this.task = task;
    }
}
