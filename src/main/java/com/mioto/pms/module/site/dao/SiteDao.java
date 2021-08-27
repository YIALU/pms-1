package com.mioto.pms.module.site.dao;

import com.mioto.pms.module.site.model.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* @auther lzc
* date 2021-04-07 17:56:51
*/
@Repository
public interface SiteDao {

    /**
    * 根据条件查询列表
    * @return
    */
    List<SiteDTO> findList(Site site);

    /**
    * 根据列名和对应的值查询对象
    * @param column
    * @param value
    * @return
    */
    Site findByColumn(@Param("column") String column, @Param("value") String value);

    /**
    * 新增对象,忽略空值
    * @return
    */
    int insertIgnoreNull(Site site);

    /**
    * 修改对象,忽略空值
    * @return
    */
    int updateIgnoreNull(Site site);

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

    List<SiteExcel> findExcelByIds(@Param("ids") String[] ids);

    /**
     * excel导入新增
     * @param list
     * @return
     */
    int insertBatch(List<Site> list);

    /**
     * 根据id查询区域地址
     * @param id
     * @return
     */
    String findAddressById(Integer id);

    /**
     * 根据id查询区域地址详情 包括省市区
     * @param id
     * @return
     */
    String findAddressDetailById(Integer id);

    SiteBO findSiteBOBySiteId(Integer SiteId);

    SiteVO findDetail(String id);
}
