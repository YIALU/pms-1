package com.mioto.pms.module.site.service;


import com.mioto.pms.module.site.model.Site;
import com.mioto.pms.module.site.model.SiteDTO;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
* @auther lzc
* date 2021-04-07 17:56:51
*/
public interface SiteService {
/**
* 根据条件查询列表
*/
List<SiteDTO> findList(Site site);

    /**
    * 根据列名和对应的值查询对象
    */
    Site findByColumn(String column, String value);

    /**
    * 新增对象,忽略空值
    */
    int insertIgnoreNull(Site site);

    /**
    * 修改对象,忽略空值
    */
    int updateIgnoreNull(Site site);

    /**
    * 根据列名和对应的值删除对象
    */
    int deleteByColumn(String column, String value);

    /**
    * 根据主键列表批量删除
    */
    int batchDelete(String[] ids);

    void exportExcel(HttpServletResponse response, String[] ids) throws IOException;

    void importExcel(List<Map<String,Object>> list);
}
