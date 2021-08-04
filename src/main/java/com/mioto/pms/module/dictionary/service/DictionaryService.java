package com.mioto.pms.module.dictionary.service;


import com.mioto.pms.module.dictionary.model.Dictionary;

import java.util.List;

/**
* @auther lzc
* date 2021-04-06 15:25:37
*/
public interface DictionaryService {
/**
* 根据条件查询列表
*/
List<Dictionary> findList(Dictionary dictionary);

    /**
    * 根据列名和对应的值查询对象
    */
    Dictionary findByColumn(String column, String value);

    /**
    * 新增对象,忽略空值
    */
    int insertIgnoreNull(Dictionary dictionary);

    /**
    * 修改对象,忽略空值
    */
    int updateIgnoreNull(Dictionary dictionary);

    /**
    * 根据列名和对应的值删除对象
    */
    int deleteByColumn(String column, String value);

    /**
    * 根据主键列表批量删除
    */
    int batchDelete(String[] ids);
}
