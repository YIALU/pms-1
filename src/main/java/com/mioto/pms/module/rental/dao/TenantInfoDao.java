package com.mioto.pms.module.rental.dao;

import com.mioto.pms.module.rental.model.TenantInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 *
 * @author qinxj
 * @date 2021-06-30 14:53:10
 */
@Mapper
public interface TenantInfoDao{

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
    TenantInfo findByColumn(@Param("column") String column,@Param("value") Object value);

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
    int deleteByColumn(@Param("column") String column,@Param("value") Object value);

    /**
     * 根据主键列表批量删除
     * @param ids
     * @return
     */
    int batchDelete(Integer[] ids);

    /**
     * 根据出租信息id查询租户列表
     * @param rentalId
     * @return
     */
    List<TenantInfo> findListByRentalId(String rentalId);
}