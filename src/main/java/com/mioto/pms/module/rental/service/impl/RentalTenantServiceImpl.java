package com.mioto.pms.module.rental.service.impl;

import org.springframework.stereotype.Service;

import com.mioto.pms.module.rental.dao.RentalTenantDao;
import com.mioto.pms.module.rental.model.RentalTenant;
import com.mioto.pms.module.rental.service.IRentalTenantService;
import javax.annotation.Resource;
import java.util.List;

/**
 *
 * @author qinxj
 * @date 2021-06-30 14:53:10
 */
@Service("rentalTenantService")
public class RentalTenantServiceImpl implements IRentalTenantService{

    @Resource
    private RentalTenantDao rentalTenantDao;

    @Override
    public List<RentalTenant> findList(RentalTenant rentalTenant) {
        return rentalTenantDao.findList(rentalTenant);
    }

    @Override
    public int insert(RentalTenant rentalTenant) {
        return rentalTenantDao.insert(rentalTenant);
    }

    @Override
    public int insertIgnoreNull(RentalTenant rentalTenant) {
        return rentalTenantDao.insertIgnoreNull(rentalTenant);
    }

    @Override
    public int update(RentalTenant rentalTenant) {
        return rentalTenantDao.update(rentalTenant);
    }

    @Override
    public int updateIgnoreNull(RentalTenant rentalTenant) {
        return rentalTenantDao.updateIgnoreNull(rentalTenant);
    }

    @Override
    public RentalTenant findByColumn(String column, Object value) {
        return rentalTenantDao.findByColumn(column,value);
    }

    @Override
    public int deleteByColumn(String column, Object value) {
        return rentalTenantDao.deleteByColumn(column,value);
    }

    @Override
    public int batchDelete(Integer[] ids) {
        return rentalTenantDao.batchDelete(ids);
    }
}