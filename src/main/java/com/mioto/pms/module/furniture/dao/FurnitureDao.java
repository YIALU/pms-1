package com.mioto.pms.module.furniture.dao;

import com.mioto.pms.module.furniture.model.Furniture;
import com.mioto.pms.module.furniture.model.FurnitureListVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* @auther lzc
* date 2021-04-21 16:22:29
*/
@Repository
public interface FurnitureDao {

    /**
    * 根据条件查询列表
    * @return
    */
    List<FurnitureListVO> findList(Furniture furniture);

    /**
    * 根据列名和对应的值查询对象
    * @param column
    * @param value
    * @return
    */
    Furniture findByColumn(@Param("column") String column, @Param("value") String value);

    /**
    * 新增对象,忽略空值
    * @return
    */
    int insertIgnoreNull(Furniture furniture);

    /**
    * 修改对象,忽略空值
    * @return
    */
    int updateIgnoreNull(Furniture furniture);

    /**
    * 根据列名和对应的值删除对象
    * @param column
    * @param value
    * @return
    */
    int deleteByColumn(@Param("column") String column, @Param("value") Object value);

    /**
    * 根据主键列表批量删除
    * @param ids
    * @return
    */
    int batchDelete(Integer[] ids);

    int insertAll(List<Furniture> furnitures);

    /**
     * 查询资产名称列表
     * @return
     */
    String[] findFurnitureNames();

    /**
     * 添加房屋与资产的关系
     * @param furnitureIds
     * @param roomId
     * @return
     */
    int addRoomRelation(@Param("array")Integer[] furnitureIds,@Param("roomId") String roomId);


    List<Furniture> findByRoomId(String roomId);

    int clearRoomRelation(String roomId);
}
