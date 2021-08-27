package com.mioto.pms.module.statistics.dao;

import com.mioto.pms.module.statistics.model.PaymentProgressVO;
import com.mioto.pms.module.statistics.model.PaymentVO;
import com.mioto.pms.module.statistics.model.RoomInfoStatisticsVO;
import com.mioto.pms.module.statistics.model.UnpairFeeCompletionVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author admin
 * @date 2021-07-29 17:12
 */
@Repository
public interface StatisticsDao {
    /**
     * 查询房屋数量
     * @param userId
     * @return
     */
    RoomInfoStatisticsVO findRoomCount(Integer userId);

    /**
     * 查询电表数量
     * @param userId
     * @return
     */
    int findMeterElectCount(Integer userId);

    /**
     * 查询水表数量
     * @param userId
     * @return
     */
    int findMeterWaterCount(Integer userId);

    /**
     * 统计收费完成度
     * @param userId
     * @return
     */
    PaymentProgressVO feeCompletion(@Param("userId") Integer userId, @Param("month") String month);

    /**
     * 欠费列表
     * @param userId
     * @return
     */
    List<UnpairFeeCompletionVO> unpairFeeCompletion(@Param("userId") Integer userId, @Param("month") String month);

    PaymentVO paymentCount(@Param("logonUserId") Integer logonUserId, @Param("type")int type);
}
