package com.mioto.pms.module.price.dao;

import com.mioto.pms.module.price.model.Price;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* @auther lzc
* date 2021-04-16 14:51:24
*/
@Repository
public interface PriceDao {

    /**
    * 根据条件查询列表
    * @return
    */
    List<Price> findList(Price price);

    /**
    * 根据列名和对应的值查询对象
    * @param column
    * @param value
    * @return
    */
    Price findByColumn(@Param("column") String column, @Param("value") String value);

    /**
    * 新增对象,忽略空值
    * @return
    */
    int insertIgnoreNull(Price price);

    /**
    * 修改对象,忽略空值
    * @return
    */
    int updateIgnoreNull(Price price);

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
     * 根据房间id查询每月房租
     * @param roomId
     * @return
     */
    Double findRentFeeByRoomId(String roomId);

    List<Price> findByRoomId(String roomId);

    List<Price> findByRentalId(String rentalId);

    int deleteById(String id);
}
