package com.mioto.pms.aop;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.mioto.pms.anno.MeterReadingAnno;
import com.mioto.pms.module.meter.MeterReadType;
import com.mioto.pms.module.meter.dao.MeterReadingDao;
import com.mioto.pms.module.meter.model.MeterReading;
import com.mioto.pms.module.rental.model.RentalDetailDTO;
import com.mioto.pms.module.rental.model.RentalInfo;
import com.mioto.pms.module.rental.service.IRentalInfoService;
import com.mioto.pms.module.room.model.RoomDetailDTO;
import com.mioto.pms.quartz.MeterReadingTask;
import com.mioto.pms.quartz.QuartzManager;
import com.mioto.pms.result.ResultData;
import com.mioto.pms.result.SystemTip;
import com.mioto.pms.utils.BaseUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author admin
 * @date 2021-07-16 15:23
 */
@Async
@Aspect
@Component
public class MeterReadingTaskAspect {

    @Resource
    private MeterReadingDao meterReadingDao;
    @Resource
    private IRentalInfoService rentalInfoService;

    @Pointcut("@annotation(com.mioto.pms.anno.MeterReadingAnno)")
    public void meterReadingTaskAspect(){}

    @AfterReturning(value = "meterReadingTaskAspect()",returning="resultData")
    public void doAfter(JoinPoint point, ResultData resultData){
        //租住办理成功，添加抄表定时任务
        if (StrUtil.equals(resultData.getCode(), SystemTip.OK.code())){
            MethodSignature signature = (MethodSignature) point.getSignature();
            MeterReadingAnno meterReadingAnno = signature.getMethod().getAnnotation(MeterReadingAnno.class);
            Object[] args = point.getArgs();
            //新增租住办理
            if (meterReadingAnno.type() == MeterReadType.INSERT){
                RentalDetailDTO rentalInfo = (RentalDetailDTO) args[0];
                String roomId = rentalInfo.getRoomId();
                addTask(roomId);
            }else if (meterReadingAnno.type() == MeterReadType.UPDATE){
                if (args[0] instanceof RentalDetailDTO){
                    RentalDetailDTO rentalInfo = (RentalDetailDTO) args[0];
                    //租住的房间id发生变化，重置定时任务
                    if (StrUtil.isNotEmpty(rentalInfo.getRoomId())){
                        new QuartzManager().deleteDynamicTask(rentalInfo.getRoomId());
                        addTask(rentalInfo.getRoomId());
                    }
                }else if (args[0] instanceof RoomDetailDTO){
                    RoomDetailDTO roomDetailDTO = (RoomDetailDTO) args[0];
                    //如果修改房间的抄表策略,并且房间正在出租，也要重置定时任务
                    if (ObjectUtil.isNotEmpty(roomDetailDTO.getMeterElect())){
                        List<RentalInfo> rentalInfoList = rentalInfoService.findByRoomIdAndStatus(roomDetailDTO.getId(),1);
                        if (CollUtil.isNotEmpty(rentalInfoList) && rentalInfoList.size() == 1) {
                            new QuartzManager().deleteDynamicTask(roomDetailDTO.getId());
                            addTask(roomDetailDTO.getId());
                        }
                    }
                }
            }else if (meterReadingAnno.type() == MeterReadType.DELETE){
                //退租，删除任务
                String rentalId =(String) args[0];
                RentalInfo rentalInfo = rentalInfoService.findByColumn("id",rentalId);
                new QuartzManager().deleteDynamicTask(rentalInfo.getRoomId());
            }
        }
    }

    /**
     * 添加定时抄表任务
     * @param roomId
     */
    private void addTask(String roomId){
        MeterReading meterReading = meterReadingDao.findByRoomId(roomId);
        if (ObjectUtil.isNotEmpty(meterReading)){
            QuartzManager quartzManager = new QuartzManager();
            //每个月的某天执行
            String dateStr = BaseUtil.createSchedulingPattern(meterReading.getDate(), meterReading.getTime());
            if (StrUtil.isNotEmpty(dateStr)) {
                final String schedulingPattern = quartzManager.createFixQuartz(dateStr);
                quartzManager.startTask(roomId, schedulingPattern, new MeterReadingTask(roomId));
            }
        }
    }


}
