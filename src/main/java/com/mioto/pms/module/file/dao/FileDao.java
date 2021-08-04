package com.mioto.pms.module.file.dao;

import com.mioto.pms.module.file.model.FileInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* @auther csl
* date 2021-03-22 15:20:48
*/
@Repository
public interface FileDao {

    /**
    * 根据条件查询列表
    * @return
    */
    List<FileInfo> findList(FileInfo file);

    /**
    * 根据列名和对应的值查询对象
    * @param column
    * @param value
    * @return
    */
    FileInfo findByColumn(@Param("column") String column, @Param("value") String value);

    /**
    * 新增对象,忽略空值
    * @return
    */
    int insertIgnoreNull(FileInfo file);

    /**
    * 修改对象,忽略空值
    * @return
    */
    int updateIgnoreNull(FileInfo file);

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

    List<FileInfo> findByIds(String[] ids);

    int batchInsert(List<FileInfo> fileInfos);
}
