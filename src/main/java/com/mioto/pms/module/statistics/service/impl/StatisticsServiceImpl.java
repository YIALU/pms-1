package com.mioto.pms.module.statistics.service.impl;

import cn.hutool.core.date.DateUtil;
import com.mioto.pms.module.device.DeviceTypeEnum;
import com.mioto.pms.module.statistics.dao.StatisticsDao;
import com.mioto.pms.module.statistics.model.*;
import com.mioto.pms.module.statistics.service.IStatisticsService;
import com.mioto.pms.utils.BaseUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author admin
 * @date 2021-07-29 17:22
 */
@Service("statisticsService")
public class StatisticsServiceImpl implements IStatisticsService {
    @Resource
    private StatisticsDao statisticsDao;

    @Override
    public RoomInfoStatisticsVO findRoomCount() {
        return statisticsDao.findRoomCount(BaseUtil.getLogonUserId());
    }

    @Override
    public PaymentProgressVO paymentProgressCount(int month) {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        String yearAndMonth = year + "-" + (month < 10 ? "0"+month : month+"");
        return statisticsDao.feeCompletion(BaseUtil.getLogonUserId(),yearAndMonth);
    }

    @Override
    public PaymentVO paymentCount(int type) {
        /*int yearOrMonth;
        if (type == 1){
            //按月统计
            yearOrMonth = Calendar.getInstance().get(Calendar.MONTH) + 1;
        }else if (type == 2){
            //按年统计
            yearOrMonth = Calendar.getInstance().get(Calendar.YEAR);
        }*/
        return statisticsDao.paymentCount(BaseUtil.getLogonUserId(),type);
    }

    @Override
    public List<EnergyVO> energy(int type, int energyType) {
        String tableName;
        if (energyType == 1){
            tableName = DeviceTypeEnum.ELECTRICITY_METER.getTableName();
        }else {
            tableName = DeviceTypeEnum.WATER_METER.getTableName();
        }
        Calendar calendar = Calendar.getInstance();
        Date beginDate;
        Date endDate;
        String wildcard;
        if (type == 1){
            beginDate = DateUtil.beginOfWeek(calendar,true).getTime();
            endDate = DateUtil.endOfWeek(calendar,true).getTime();
            wildcard = "%w";
        }else if (type == 2){
            beginDate = DateUtil.beginOfMonth(calendar).getTime();
            endDate = DateUtil.endOfMonth(calendar).getTime();
            wildcard = "%e";
        }else {
            beginDate = DateUtil.beginOfYear(calendar).getTime();
            endDate = DateUtil.endOfYear(calendar).getTime();
            wildcard = "%c";
        }
        EnergyBO energyBO = new EnergyBO();
        energyBO.setEndDate(endDate);
        energyBO.setBeginDate(beginDate);
        energyBO.setTableName(tableName);
        energyBO.setType(type);
        energyBO.setWildcard(wildcard);
        energyBO.setLogonUserId(BaseUtil.getLogonUserId());
        return statisticsDao.energy(energyBO);
    }
}
