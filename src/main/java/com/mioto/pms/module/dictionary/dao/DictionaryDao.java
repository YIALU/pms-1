package com.mioto.pms.module.dictionary.dao;

import com.mioto.pms.module.dictionary.model.Dictionary;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
* @auther lzc
* date 2021-04-06 15:25:37
*/
@Repository
public interface DictionaryDao {

    /**
    * 根据条件查询列表
    * @return
    */
    List<Dictionary> findList(Dictionary dictionary);

    /**
    * 根据列名和对应的值查询对象
    * @param column
    * @param value
    * @return
    */
    Dictionary findByColumn(@Param("column") String column, @Param("value") String value);

    /**
    * 新增对象,忽略空值
    * @return
    */
    int insertIgnoreNull(Dictionary dictionary);

    /**
    * 修改对象,忽略空值
    * @return
    */
    int updateIgnoreNull(Dictionary dictionary);

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

    Dictionary findDictionary(Map<String, Object> map);
}
