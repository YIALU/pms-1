package com.mioto.pms.module.statistics.service;

import com.mioto.pms.module.statistics.model.EnergyVO;
import com.mioto.pms.module.statistics.model.PaymentProgressVO;
import com.mioto.pms.module.statistics.model.PaymentVO;
import com.mioto.pms.module.statistics.model.RoomInfoStatisticsVO;

import java.util.List;

/**
 * @author admin
 * @date 2021-07-29 17:11
 */
public interface IStatisticsService {
    RoomInfoStatisticsVO findRoomCount();

    PaymentProgressVO paymentProgressCount(int month);

    PaymentVO paymentCount(int type);

    List<EnergyVO> energy(int type, int energyType);
}
