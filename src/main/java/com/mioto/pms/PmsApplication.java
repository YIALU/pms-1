package com.mioto.pms;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.mioto.pms.cache.RegionCache;
import com.mioto.pms.component.SchedulerType;
import com.mioto.pms.module.basic.model.Scheduler;
import com.mioto.pms.module.basic.service.IBasicService;
import com.mioto.pms.module.meter.model.RoomMeterReading;
import com.mioto.pms.module.meter.service.MeterReadingService;
import com.mioto.pms.netty.NettyServer;
import com.mioto.pms.quartz.MeterReadingDayTask;
import com.mioto.pms.quartz.MeterReadingMonthTask;
import com.mioto.pms.quartz.QuartzManager;
import com.mioto.pms.utils.BaseUtil;
import com.mioto.pms.utils.SpringBeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;
import java.util.Map;

/**
 * @author admin
 * @date 2021-06-01 18:51
 */
@EnableAsync
@EnableSwagger2
@SpringBootApplication
@MapperScan("com.mioto.pms.module.*.dao")
@Slf4j
public class PmsApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(PmsApplication.class, args);
        nettyStart(run);
        addMeterReadingMonthTask();
        addMeterReadingDayTask();
        initDictionary(run);
        addOverdueNotifyTask();
    }

    /**
     * 启动netty
     * @param context
     */
    private static void nettyStart(ConfigurableApplicationContext context){
        Map<String, NettyServer> nettyServers = context.getBeansOfType(NettyServer.class);
        nettyServers.entrySet().stream().forEach(entry -> entry.getValue().start());
    }

    private static void initDictionary(ConfigurableApplicationContext context){
        RegionCache regionCache = context.getBean(RegionCache.class);
        regionCache.init();
    }

    /**
     * 服务器启动，添加每月抄表任务
     */
    private static void addMeterReadingMonthTask(){
        List<RoomMeterReading> meterReadingList = SpringBeanUtil.getBean(MeterReadingService.class).findRentingMeterReadings();
        if (CollUtil.isNotEmpty(meterReadingList)){
            meterReadingList.forEach(meterReading -> {
                //每个月的某天执行
                String dateStr = BaseUtil.createSchedulingPattern(meterReading.getDate(), meterReading.getTime());
                if (StrUtil.isNotEmpty(dateStr)) {
                    QuartzManager quartzManager = new QuartzManager();
                    final String schedulingPattern = quartzManager.createFixQuartz(dateStr);
                    quartzManager.startTask(meterReading.getRoomId(), schedulingPattern, new MeterReadingMonthTask(meterReading.getRoomId()));
                    log.info("创建每月抄表定时任务 - 抄表时间每月{}号,房间id - {}",meterReading.getDate(),meterReading.getRoomId());
                }
            });
        }
    }

    private static void addMeterReadingDayTask(){
        String dateStr = "23:55:00";
        QuartzManager quartzManager = new QuartzManager();
        final String schedulingPattern = quartzManager.createFixQuartz(dateStr);
        quartzManager.startTask("meterReadingDay", schedulingPattern, new MeterReadingDayTask());
        log.info("创建每日抄表定时任务 - 抄表时间每天 - {} ", dateStr);
    }

    private static void addOverdueNotifyTask(){
        List<Scheduler> schedulerList =  SpringBeanUtil.getBean(IBasicService.class).findListByType(SchedulerType.OVERDUE_NOTIFY.getType());
        log.info("加载催收短信定时任务,定时任务数量 - {} ",schedulerList.size());
        for (Scheduler scheduler : schedulerList) {
            QuartzManager quartzManager = new QuartzManager();
            final String schedulingPattern = quartzManager.createFixQuartz(scheduler.getSchedulerTime());
            quartzManager.startTask(scheduler.getId(), schedulingPattern, JSONUtil.toBean(scheduler.getSchedulerParam(),MeterReadingDayTask.class));
            log.info("创建催收短信定时任务 - 任务执行日期 - {} ",scheduler.getSchedulerTime());
        }
    }
}
