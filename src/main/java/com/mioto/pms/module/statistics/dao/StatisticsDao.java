package com.mioto.pms.module.statistics.dao;

import com.mioto.pms.module.statistics.model.RoomInfoStatisticsVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

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
     * 统计收费完成度
     * @param userId
     * @return
     */
    Double feeCompletion(@Param("userId") Integer userId,@Param("month") String month);
}
