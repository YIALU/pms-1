package com.mioto.pms.module.price.service;


import com.mioto.pms.module.price.model.Price;

import java.util.List;

/**
* @auther lzc
* date 2021-04-16 14:51:24
*/
public interface PriceService {
/**
* 根据条件查询列表
*/
List<Price> findList(Price price);

    /**
    * 根据列名和对应的值查询对象
    */
    Price findByColumn(String column, String value);

    /**
    * 新增对象,忽略空值
    */
    int insertIgnoreNull(Price price);

    /**
    * 修改对象,忽略空值
    */
    int updateIgnoreNull(Price price);

    /**
    * 根据列名和对应的值删除对象
    */
    int deleteByColumn(String column, String value);

    /**
    * 根据主键列表批量删除
    */
    int batchDelete(String[] ids);

    /**
     * 根据房屋id查询收费策略列表
     * @param roomId
     * @return
     */
    List<Price> findByRoomId(String roomId);

}
