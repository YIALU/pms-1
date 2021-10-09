package com.mioto.pms.quartz;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.cron.CronUtil;
import cn.hutool.cron.task.Task;
import com.mioto.pms.module.basic.service.IBasicService;
import com.mioto.pms.module.notify.model.OverdueNotifyBO;
import com.mioto.pms.module.notify.service.IOverdueNotifyService;
import com.mioto.pms.utils.SpringBeanUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @author admin
 * @date 2021-09-04 15:20
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OverdueNotifyTask implements Task {

    private String[] costInfoIds;
    private int overdueDay;
    private String schedulerId;

    @Override
    public void execute() {
        List<OverdueNotifyBO> notifyBOList = SpringBeanUtil.getBean(IOverdueNotifyService.class).findByCostInfoIds(costInfoIds);
        if (CollUtil.isNotEmpty(notifyBOList)) {
            IBasicService basicService = SpringBeanUtil.getBean(IBasicService.class);
            for (OverdueNotifyBO overdueNotifyBO : notifyBOList) {
                basicService.sendoVerdueNotifySms(overdueNotifyBO.getPhone());
            }
        }
        CronUtil.remove(schedulerId);
        SpringBeanUtil.getBean(IBasicService.class).removeScheduler(schedulerId);
    }
}
