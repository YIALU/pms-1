package com.mioto.pms.module.meter.dao;

import com.mioto.pms.module.meter.model.MeterData;
import com.mioto.pms.module.meter.model.MeterReading;
import com.mioto.pms.module.meter.model.RoomMeterReading;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* @auther lzc
* date 2021-04-16 16:13:24
*/
@Repository
public interface MeterReadingDao {

    /**
    * 根据条件查询列表
    * @return
    */
    List<MeterReading> findList(MeterReading meterReading);

    /**
    * 根据列名和对应的值查询对象
    * @param column
    * @param value
    * @return
    */
    MeterReading findByColumn(@Param("column") String column, @Param("value") String value);

    /**
    * 新增对象,忽略空值
    * @return
    */
    int insertIgnoreNull(MeterReading meterReading);

    /**
    * 修改对象,忽略空值
    * @return
    */
    int updateIgnoreNull(MeterReading meterReading);

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

    MeterReading findByRoomId(String roomId);

    MeterReading findByRentalId(String rentalId);

    List<RoomMeterReading> findRentingMeterReadings();

    int saveMeterData(@Param("meterData") MeterData meterData,@Param("tableName")String tableName);

    double findDataByDeviceId(@Param("deviceId")int deviceId,@Param("tableName") String tableName);

    MeterData findLastData(@Param("tableName")String tableName,@Param("deviceId") int deviceId);

    int updateMeterData(@Param("meterData")MeterData meterData,@Param("tableName") String tableName);
}
