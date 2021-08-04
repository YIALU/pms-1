package com.mioto.pms.module.site.service.impl;

import cn.hutool.core.io.IoUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.mioto.pms.module.site.dao.SiteDao;
import com.mioto.pms.module.site.model.Site;
import com.mioto.pms.module.site.model.SiteDTO;
import com.mioto.pms.module.site.model.SiteExcel;
import com.mioto.pms.module.site.service.SiteService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
* @auther lzc
* date 2021-04-07 17:56:51
*/
@Service("siteService")
public class SiteServiceImpl implements SiteService {

    @Resource
    private SiteDao siteDao;

    /**
    * 根据条件查询列表
    */
    @Override
    public List<SiteDTO> findList(Site site) {
        return siteDao.findList(site);
    }

    /**
    * 根据列名和对应的值查询对象
    */
    @Override
    public Site findByColumn(String column, String value) {
        return siteDao.findByColumn(column, value);
    }

    /**
    * 新增对象,忽略空值
    */
    @Override
    public int insertIgnoreNull(Site site) {

         return siteDao.insertIgnoreNull(site);
    }

    /**
    * 修改对象,忽略空值
    */
    @Override
    public int updateIgnoreNull(Site site) {
        return siteDao.updateIgnoreNull(site);
    }

    /**
    * 根据列名和对应的值删除对象
    */
    @Override
    public int deleteByColumn(String column, String value) {
        return siteDao.deleteByColumn(column,value);
    }

    /**
    * 根据主键列表批量删除
    */
    @Override
    public int batchDelete(String[] ids) {
        return siteDao.batchDelete(ids);
    }

    /**
     * 根据ids导出excel
     * @param response
     * @param ids
     */
    @Override
    public void exportExcel(HttpServletResponse response, String[] ids) throws IOException {
      List<SiteExcel> list =   siteDao.findExcelByIds(ids);
        // 通过工具类创建writer，默认创建xls格式
        Map<String,String> map= new LinkedHashMap<>();
        map.put("number","编号");
        map.put("provinceName","省");
        map.put("cityName","市");
        map.put("districtName","区/县");
        map.put("address","详细地址");
        map.put("username","所属账号");
        ExcelWriter writer = ExcelUtil.getWriter();
        writer.setHeaderAlias(map);
       // 一次性写出内容，使用默认样式，强制输出标题
        writer.write(list, true);


         //out为OutputStream，需要写出到的目标流
        //response为HttpServletResponse对象
        writer.autoSizeColumnAll();
        response.setContentType("application/vnd.ms-excel;charset=utf-8");

        response.setHeader("Content-Disposition","attachment;filename="+URLEncoder.encode("区域管理.xls","UTF-8"));

        ServletOutputStream out=response.getOutputStream();

        writer.flush(out, true);
         // 关闭writer，释放内存
        writer.close();
      //此处记得关闭输出Servlet流
        IoUtil.close(out);
    }

    /**
     * 根据excel导入区域信息
     * @param list
     */
    @Override
    public void importExcel(List<Map<String, Object>> list) {
     List<SiteExcel> list1=   new ArrayList<>();
     for(Map map:list){
         SiteExcel siteExcel = new SiteExcel();
         siteExcel.setNumber(map.get("编号").toString());
         siteExcel.setProvinceName(map.get("省").toString());
         siteExcel.setCityName(map.get("市").toString());
         siteExcel.setDistrictName(map.get("区/县").toString());
         siteExcel.setAddress(map.get("详细地址").toString());
         siteExcel.setUsername(map.get("所属账号").toString());
         list1.add(siteExcel);
     }
     int i=  siteDao.insertExcel(list1);

    }

}
