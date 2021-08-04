package com.mioto.pms.module.meter.service;


import com.mioto.pms.module.meter.model.MeterReading;
import com.mioto.pms.module.meter.model.RoomMeterReading;

import java.util.List;

/**
* @auther lzc
* date 2021-04-16 16:13:24
*/
public interface MeterReadingService {
/**
* 根据条件查询列表
*/
List<MeterReading> findList(MeterReading meterReading);

    /**
    * 根据列名和对应的值查询对象
    */
    MeterReading findByColumn(String column, String value);

    /**
    * 新增对象,忽略空值
    */
    int insertIgnoreNull(MeterReading meterReading);

    /**
    * 修改对象,忽略空值
    */
    int updateIgnoreNull(MeterReading meterReading);

    /**
    * 根据列名和对应的值删除对象
    */
    int deleteByColumn(String column, String value);

    /**
    * 根据主键列表批量删除
    */
    int batchDelete(String[] ids);

    /**
     * 查询所有在租房屋抄表策略
     * @return
     */
    List<RoomMeterReading> findRentingMeterReadings();
}
