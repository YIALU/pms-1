package com.mioto.pms.module.price.service.impl;

import com.mioto.pms.module.price.dao.PriceDao;
import com.mioto.pms.module.price.model.Price;
import com.mioto.pms.module.price.service.PriceService;
import com.mioto.pms.utils.BaseUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
* @auther lzc
* date 2021-04-16 14:51:24
*/
@Service("priceService")
public class PriceServiceImpl implements PriceService {

    @Resource
    private PriceDao priceDao;

    /**
    * 根据条件查询列表
    */
    @Override
    public List<Price> findList(Price price) {
        price.setUserId(BaseUtil.getLogonUserId());
        return priceDao.findList(price);
    }

    /**
    * 根据列名和对应的值查询对象
    */
    @Override
    public Price findByColumn(String column, String value) {
        return priceDao.findByColumn(column, value);
    }

    /**
    * 新增对象,忽略空值
    */
    @Override
    public int insertIgnoreNull(Price price) {
        price.setCreationTime(new Date());
        price.setUserId(BaseUtil.getLoginUser().getId());
        return priceDao.insertIgnoreNull(price);
    }

    /**
    * 修改对象,忽略空值
    */
    @Override
    public int updateIgnoreNull(Price price) {
        return priceDao.updateIgnoreNull(price);
    }

    /**
    * 根据列名和对应的值删除对象
    */
    @Override
    public int deleteByColumn(String column, String value) {
        return priceDao.deleteByColumn(column,value);
    }

    @Override
    public int deleteById(String id) {
        return priceDao.deleteById(id);
    }

    /**
    * 根据主键列表批量删除
    */
    @Override
    public int batchDelete(String[] ids) {
        return priceDao.batchDelete(ids);
    }

    @Override
    public List<Price> findByRoomId(String roomId) {
        return priceDao.findByRoomId(roomId);
    }

    @Override
    public List<Price> findByRentalId(String rentalId) {
        return priceDao.findByRentalId(rentalId);
    }
}
