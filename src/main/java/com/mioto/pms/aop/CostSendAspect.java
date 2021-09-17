package com.mioto.pms.aop;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.mioto.pms.component.SchedulerType;
import com.mioto.pms.module.basic.model.Scheduler;
import com.mioto.pms.module.basic.service.IBasicService;
import com.mioto.pms.module.notify.model.OverdueNotify;
import com.mioto.pms.module.notify.model.OverdueNotifyBO;
import com.mioto.pms.module.notify.service.IOverdueNotifyService;
import com.mioto.pms.quartz.OverdueNotifyTask;
import com.mioto.pms.quartz.QuartzManager;
import com.mioto.pms.result.ResultData;
import com.mioto.pms.result.SystemTip;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 账单发送成功后，根据催收规则，发送短信
 * @author admin
 * @date 2021-09-04 14:44
 */
@Async
@Aspect
@Component
public class CostSendAspect {
    @Resource
    private IOverdueNotifyService overdueNotifyService;
    @Resource
    private IBasicService basicService;

    @Pointcut("execution(* com.mioto.pms.module.*.controller.CostInfoController.batchSend(..))")
    public void costSendAspect() {
    }

    @AfterReturning(value = "costSendAspect()",returning="resultData")
    public void doAfter(JoinPoint point, ResultData resultData){
        //接口返回成功，添加账单信息
        if (StrUtil.equals(resultData.getCode(), SystemTip.OK.code())) {
            Object[] args = point.getArgs();
            String[] costInfoIds = (String[]) args[0];
            OverdueNotify overdueNotify = overdueNotifyService.find();
            if (ObjectUtil.isNotEmpty(overdueNotify)){
                //发送通知短信
                if (overdueNotify.getImmediateNotice() == 1){
                    //逾期多少天发送,添加定时任务
                    if (overdueNotify.getOverdue() == 1){
                        int overdueDay = overdueNotify.getOverdueDay();
                        QuartzManager quartzManager = new QuartzManager();
                        String dateStr = DateUtil.offsetDay(new Date(),overdueDay).toString(DatePattern.NORM_DATETIME_PATTERN);
                        final String schedulingPattern = quartzManager.createFixQuartz(dateStr);
                        String schedulerId = IdUtil.simpleUUID();
                        OverdueNotifyTask overdueNotifyTask = new OverdueNotifyTask(costInfoIds,overdueDay,schedulerId);
                        quartzManager.startTask(schedulerId, schedulingPattern,overdueNotifyTask);
                        Scheduler scheduler = new Scheduler();
                        scheduler.setId(schedulerId);
                        scheduler.setSchedulerTime(dateStr);
                        scheduler.setSchedulerType(SchedulerType.OVERDUE_NOTIFY.getType());
                        scheduler.setSchedulerParam(JSONUtil.parseObj(overdueNotifyTask).toString());
                        basicService.saveScheduler(scheduler);
                    }else {
                        //立即发送短信
                        List<OverdueNotifyBO> notifyBOList = overdueNotifyService.findByCostInfoIds(costInfoIds);
                        if (CollUtil.isNotEmpty(notifyBOList)) {
                            for (OverdueNotifyBO overdueNotifyBO : notifyBOList) {
                                basicService.sendoVerdueNotifySms(overdueNotifyBO.getPhone());
                            }
                        }
                    }
                }
            }
        }
    }
}
