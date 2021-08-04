package com.mioto.pms.module.personal.service;


import com.mioto.pms.module.personal.model.Personal;
import com.mioto.pms.module.personal.model.PersonalDTO;

import java.util.List;

/**
* @auther lzc
* date 2021-04-08 18:00:58
*/
public interface PersonalService {
/**
* 根据条件查询列表
*/
List<PersonalDTO> findList(Personal personal);

    /**
    * 根据列名和对应的值查询对象
    */
    Personal findByColumn(String column, String value);

    /**
    * 新增对象,忽略空值
    */
    int insertIgnoreNull(Personal personal);

    /**
    * 修改对象,忽略空值
    */
    int updateIgnoreNull(Personal personal);

    /**
    * 根据列名和对应的值删除对象
    */
    int deleteByColumn(String column, String value);

    /**
    * 根据主键列表批量删除
    */
    int batchDelete(String[] ids);

    List<Personal> findOwmer();

}
