package com.mioto.pms.module.rental.service;

import com.mioto.pms.module.rental.model.RentalTenant;
import java.util.List;

/**
 * IRentalTenantService
 *
 * @author qinxj
 * @date 2021-06-30 14:53:10
 */
public interface IRentalTenantService{

    /**
     * 根据条件查询列表
     * @param rentalTenant
     * @return
     */
    List<RentalTenant> findList(RentalTenant rentalTenant);

    /**
     * 根据列名和对应的值查询对象
     * @param column
     * @param value
     * @return
     */
    RentalTenant findByColumn(String column, Object value);

    /**
     * 新增对象
     * @param rentalTenant
     * @return
     */
    int insert(RentalTenant rentalTenant);

    /**
     * 新增对象,忽略空值
     * @param rentalTenant
     * @return
     */
    int insertIgnoreNull(RentalTenant rentalTenant);

    /**
     * 修改对象
     * @param rentalTenant
     * @return
     */
    int update(RentalTenant rentalTenant);

    /**
     * 修改对象,忽略空值
     * @param rentalTenant
     * @return
     */
    int updateIgnoreNull(RentalTenant rentalTenant);

    /**
     * 根据列名和对应的值删除对象
     * @param column
     * @param value
     * @return
     */
    int deleteByColumn(String column, Object value);

    /**
     * 根据主键列表批量删除
     * @param ids
     * @return
     */
    int batchDelete(Integer[] ids);
}