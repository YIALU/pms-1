package com.mioto.pms.module.statistics.service;

import com.mioto.pms.module.statistics.model.PaymentProgressVO;
import com.mioto.pms.module.statistics.model.RoomInfoStatisticsVO;

/**
 * @author admin
 * @date 2021-07-29 17:11
 */
public interface IStatisticsService {
    RoomInfoStatisticsVO findRoomCount();

    PaymentProgressVO paymentProgressCount(String month);
}
