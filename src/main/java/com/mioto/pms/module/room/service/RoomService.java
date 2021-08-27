package com.mioto.pms.module.room.service;


import com.mioto.pms.module.room.model.*;

import java.util.List;

/**
* @auther lzc
* date 2021-04-28 15:49:17
*/
public interface RoomService {
    /**
    * 根据条件查询列表
    */
    List<RoomListVO> findList(Integer siteId,String deviceId);

    /**
    * 根据列名和对应的值查询对象
    */
    Room findByColumn(String column, String value);

    /**
    * 新增对象,忽略空值
    */
    int insertIgnoreNull(RoomDetailDTO roomDetailDTO);

    /**
    * 修改对象,忽略空值
    */
    int updateIgnoreNull(RoomDetailDTO room);

    /**
    * 根据列名和对应的值删除对象
    */
    int deleteByColumn(String column, String value);

    /**
    * 根据主键列表批量删除
    */
    int batchDelete(String[] ids);

    /**
     * 根据id查询房屋详情
     * @param roomId
     * @return
     */
    RoomDetailVO findDetailById(String roomId);

    List<WxFreeRoomVO> findFreeRooms(String address);

    List<String> findDynamicCostTypes(String roomId);
}
