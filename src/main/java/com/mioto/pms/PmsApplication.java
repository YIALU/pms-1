package com.mioto.pms;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.mioto.pms.cache.RegionCache;
import com.mioto.pms.module.meter.model.RoomMeterReading;
import com.mioto.pms.module.meter.service.MeterReadingService;
import com.mioto.pms.netty.TcpServer;
import com.mioto.pms.quartz.MeterReadingTask;
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
        //nettyStart(run);
        addMeterReadingTask();
        initDictionary(run);
    }

    /**
     * 启动netty
     * @param context
     */
    private static void nettyStart(ConfigurableApplicationContext context){
        TcpServer tcpServer = context.getBean(TcpServer.class);
        tcpServer.start();
    }

    private static void initDictionary(ConfigurableApplicationContext context){
        RegionCache regionCache = context.getBean(RegionCache.class);
        regionCache.init();
    }

    /**
     * 服务器启动，添加抄表任务
     */
    private static void addMeterReadingTask(){
        List<RoomMeterReading> meterReadingList = SpringBeanUtil.getBean(MeterReadingService.class).findRentingMeterReadings();
        if (CollUtil.isNotEmpty(meterReadingList)){
            meterReadingList.forEach(meterReading -> {
                //每个月的某天执行
                String dateStr = BaseUtil.createSchedulingPattern(meterReading.getDate(), meterReading.getTime());
                if (StrUtil.isNotEmpty(dateStr)) {
                    QuartzManager quartzManager = new QuartzManager();
                    final String schedulingPattern = quartzManager.createFixQuartz(dateStr);
                    quartzManager.startTask(meterReading.getRoomId(), schedulingPattern, new MeterReadingTask(meterReading.getRoomId()));
                    log.info("创建抄表定时任务 - 抄表时间 - {},房间id - {}",meterReading.getDate(),meterReading.getRoomId());
                }
            });
        }
    }
}
