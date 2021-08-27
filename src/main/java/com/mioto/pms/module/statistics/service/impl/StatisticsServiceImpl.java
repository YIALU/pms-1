package com.mioto.pms.module.statistics.service.impl;

import com.mioto.pms.module.statistics.dao.StatisticsDao;
import com.mioto.pms.module.statistics.model.PaymentProgressVO;
import com.mioto.pms.module.statistics.model.PaymentVO;
import com.mioto.pms.module.statistics.model.RoomInfoStatisticsVO;
import com.mioto.pms.module.statistics.service.IStatisticsService;
import com.mioto.pms.utils.BaseUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Calendar;

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
        int yearOrMonth;
        if (type == 1){
            //按月统计
            yearOrMonth = Calendar.getInstance().get(Calendar.MONTH) + 1;
        }else if (type == 2){
            //按年统计
            yearOrMonth = Calendar.getInstance().get(Calendar.YEAR);
        }
        return statisticsDao.paymentCount(BaseUtil.getLogonUserId(),type);
    }
}
