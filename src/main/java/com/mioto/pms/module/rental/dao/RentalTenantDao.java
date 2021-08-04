package com.mioto.pms.module.rental.dao;

import com.mioto.pms.module.rental.model.RentalTenant;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 *
 * @author qinxj
 * @date 2021-06-30 14:53:10
 */
@Mapper
public interface RentalTenantDao{

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
    RentalTenant findByColumn(@Param("column") String column,@Param("value") Object value);

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
    int deleteByColumn(@Param("column") String column,@Param("value") Object value);

    /**
     * 根据主键列表批量删除
     * @param ids
     * @return
     */
    int batchDelete(Integer[] ids);
}