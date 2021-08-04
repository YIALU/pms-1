package com.mioto.pms.module.file.service;


import com.mioto.pms.module.file.model.FileInfo;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
* @auther csl
* date 2021-03-22 15:20:48
*/
public interface FileService {
/**
* 根据条件查询列表
*/
List<FileInfo> findList(FileInfo file);

    /**
    * 根据列名和对应的值查询对象
    */
    FileInfo findByColumn(String column, String value);

    /**
    * 新增对象,忽略空值
    */
    int insertIgnoreNull(FileInfo file);

    /**
    * 修改对象,忽略空值
    */
    int updateIgnoreNull(FileInfo file);

    /**
    * 根据列名和对应的值删除对象
    */
    int deleteByColumn(String column, String value);

    /**
    * 根据主键列表批量删除
    */
    int batchDelete(String[] ids);

    void downloadCode(HttpServletResponse response, String[] ids);

    /**
     * 批量新增
     * @param fileInfos
     * @return
     */
    int batchInsert(List<FileInfo> fileInfos);
}
