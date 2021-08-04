package com.mioto.pms.module.statistics.service.impl;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import com.mioto.pms.module.statistics.dao.StatisticsDao;
import com.mioto.pms.module.statistics.model.PaymentProgressVO;
import com.mioto.pms.module.statistics.model.RoomInfoStatisticsVO;
import com.mioto.pms.module.statistics.service.IStatisticsService;
import com.mioto.pms.utils.BaseUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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
    public PaymentProgressVO paymentProgressCount(String month) {
        Double feeCompletion = statisticsDao.feeCompletion(BaseUtil.getLogonUserId(),month);
        PaymentProgressVO paymentProgressVO = new PaymentProgressVO();
        if(ObjectUtil.isNotEmpty(feeCompletion)) {
            paymentProgressVO.setFeeCompletion(feeCompletion);
        }
        return paymentProgressVO;
    }

}
