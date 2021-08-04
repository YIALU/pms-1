package com.mioto.pms.module.rental.service;

import com.mioto.pms.module.rental.model.TenantInfo;
import java.util.List;

/**
 * ITenantInfoService
 *
 * @author qinxj
 * @date 2021-06-30 14:53:10
 */
public interface ITenantInfoService{

    /**
     * 根据条件查询列表
     * @param tenantInfo
     * @return
     */
    List<TenantInfo> findList(TenantInfo tenantInfo);

    /**
     * 根据列名和对应的值查询对象
     * @param column
     * @param value
     * @return
     */
    TenantInfo findByColumn(String column, Object value);

    /**
     * 新增对象
     * @param tenantInfo
     * @return
     */
    int insert(TenantInfo tenantInfo);

    /**
     * 新增对象,忽略空值
     * @param tenantInfo
     * @return
     */
    int insertIgnoreNull(TenantInfo tenantInfo);

    /**
     * 修改对象
     * @param tenantInfo
     * @return
     */
    int update(TenantInfo tenantInfo);

    /**
     * 修改对象,忽略空值
     * @param tenantInfo
     * @return
     */
    int updateIgnoreNull(TenantInfo tenantInfo);

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