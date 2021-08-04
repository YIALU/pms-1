package com.mioto.pms.module.room.dao;

import com.mioto.pms.module.price.model.Price;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author admin
 * @date 2021-07-05 10:28
 */
@Repository
public interface RoomPricingStrategyDao {

    /**
     * 添加房屋的收费策略列表
     * @param roomId
     * @param strategyIds
     * @return
     */
    int insertRelations(@Param("roomId")String roomId,@Param("array")Integer[] strategyIds);

    /**
     * 根据房屋id查询对应的收费策略列表
     * @param roomId
     * @return
     */
    List<Price> findStrategysByRoomId(String roomId);

    /**
     * 删除房屋的收费策略
     * @param roomId
     * @return
     */
    int delByRoomId(String roomId);
}
