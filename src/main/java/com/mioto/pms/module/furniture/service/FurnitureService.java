package com.mioto.pms.module.furniture.service;


import com.mioto.pms.module.furniture.model.Furniture;
import com.mioto.pms.module.furniture.model.FurnitureListVO;

import java.util.List;

/**
* @auther lzc
* date 2021-04-21 16:22:29
*/
public interface FurnitureService {
    /**
    * 根据条件查询列表
    */
    List<FurnitureListVO> findList(Furniture furniture);

    /**
    * 根据列名和对应的值查询对象
    */
    Furniture findByColumn(String column, String value);

    /**
    * 新增对象,忽略空值
    */
    int insertIgnoreNull(Furniture furniture);

    /**
    * 修改对象,忽略空值
    */
    int updateIgnoreNull(Furniture furniture);

    /**
    * 根据列名和对应的值删除对象
    */
    int deleteByColumn(String column, Object value);

    /**
    * 根据主键列表批量删除
    */
    int batchDelete(Integer[] ids);

    /**
     * 批量新增资产
     * @param furnitures
     * @return
     */
    int insertAll(List<Furniture> furnitures);

    String[] findFurnitureNames();

    /**
     * 添加房屋与资产的关系
     * @param furnitureIds
     * @param roomId
     * @return
     */
    int addRoomRelation(Integer[] furnitureIds, String roomId);

    /**
     * 删除房屋包含的所有资产
     * @param roomId
     * @return
     */
    int clearRoomRelation(String roomId);

}
