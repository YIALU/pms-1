package com.mioto.pms.module.arrears.service;


import com.mioto.pms.module.arrears.model.Arrears;

import java.util.List;

/**
* @auther lzc
* date 2021-04-28 15:31:26
*/
public interface ArrearsService {
/**
* 根据条件查询列表
*/
List<Arrears> findList(Arrears arrears);

    /**
    * 根据列名和对应的值查询对象
    */
    Arrears findByColumn(String column, String value);

    /**
    * 新增对象,忽略空值
    */
    int insertIgnoreNull(Arrears arrears);

    /**
    * 修改对象,忽略空值
    */
    int updateIgnoreNull(Arrears arrears);

    /**
    * 根据列名和对应的值删除对象
    */
    int deleteByColumn(String column, String value);

    /**
    * 根据主键列表批量删除
    */
    int batchDelete(String[] ids);
}
