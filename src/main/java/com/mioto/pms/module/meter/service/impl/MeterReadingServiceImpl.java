package com.mioto.pms.module.meter.service.impl;

import com.mioto.pms.module.meter.dao.MeterReadingDao;
import com.mioto.pms.module.meter.model.MeterReading;
import com.mioto.pms.module.meter.model.RoomMeterReading;
import com.mioto.pms.module.meter.service.MeterReadingService;
import com.mioto.pms.utils.BaseUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
* @auther lzc
* date 2021-04-16 16:13:24
*/
@Service("meterReadingService")
public class MeterReadingServiceImpl implements MeterReadingService {

    @Resource
    private MeterReadingDao meterReadingDao;

    /**
    * 根据条件查询列表
    */
    @Override
    public List<MeterReading> findList(MeterReading meterReading) {
        meterReading.setUserId(BaseUtil.getLogonUserId());
        return meterReadingDao.findList(meterReading);
    }

    /**
    * 根据列名和对应的值查询对象
    */
    @Override
    public MeterReading findByColumn(String column, String value) {
        return meterReadingDao.findByColumn(column, value);
    }

    /**
    * 新增对象,忽略空值
    */
    @Override
    public int insertIgnoreNull(MeterReading meterReading) {
        meterReading.setUserId(BaseUtil.getLoginUser().getId());
        return meterReadingDao.insertIgnoreNull(meterReading);
    }

    /**
    * 修改对象,忽略空值
    */
    @Override
    public int updateIgnoreNull(MeterReading meterReading) {
        return meterReadingDao.updateIgnoreNull(meterReading);
    }

    /**
    * 根据列名和对应的值删除对象
    */
    @Override
    public int deleteByColumn(String column, String value) {
        return meterReadingDao.deleteByColumn(column,value);
    }

    /**
    * 根据主键列表批量删除
    */
    @Override
    public int batchDelete(String[] ids) {
        return meterReadingDao.batchDelete(ids);
    }

    @Override
    public List<RoomMeterReading> findRentingMeterReadings() {
        return meterReadingDao.findRentingMeterReadings();
    }
}
