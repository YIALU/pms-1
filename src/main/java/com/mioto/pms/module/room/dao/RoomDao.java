package com.mioto.pms.module.room.dao;

import com.mioto.pms.module.room.model.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* @auther lzc
* date 2021-04-28 15:49:17
*/
@Repository
public interface RoomDao {

    /**
    * 根据条件查询列表
    * @return
    */
    List<RoomListVO> findList(Room room);

    /**
    * 根据列名和对应的值查询对象
    * @param column
    * @param value
    * @return
    */
    Room findByColumn(@Param("column") String column, @Param("value") String value);

    /**
    * 新增对象,忽略空值
    * @return
    */
    int insertIgnoreNull(RoomDetailDTO roomDetailDTO);

    /**
    * 修改对象,忽略空值
    * @return
    */
    int updateIgnoreNull(RoomDetailDTO room);

    /**
    * 根据列名和对应的值删除对象
    * @param column
    * @param value
    * @return
    */
    int deleteByColumn(@Param("column") String column, @Param("value") String value);

    /**
    * 根据主键列表批量删除
    * @param ids
    * @return
    */
    int batchDelete(String[] ids);

    /**
     * 根据id查询房屋详情
     * @param roomId
     * @return
     */
    RoomDetailVO findDetailById(String roomId);


    /**
     * 查询账单详情里的房屋信息
     * @param roomId
     * @return
     */
    CostRoomVO findCostRoom(String roomId);
}
