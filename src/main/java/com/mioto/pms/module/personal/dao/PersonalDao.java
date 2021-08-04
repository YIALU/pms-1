package com.mioto.pms.module.personal.dao;

import com.mioto.pms.module.personal.model.Personal;
import com.mioto.pms.module.personal.model.PersonalDTO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* @auther lzc
* date 2021-04-08 18:00:58
*/
@Repository
public interface PersonalDao {

    /**
    * 根据条件查询列表
    * @return
    */
    List<PersonalDTO> findList(Personal personal);

    /**
    * 根据列名和对应的值查询对象
    * @param column
    * @param value
    * @return
    */
    Personal findByColumn(@Param("column") String column, @Param("value") String value);

    /**
    * 新增对象,忽略空值
    * @return
    */
    int insertIgnoreNull(Personal personal);

    /**
    * 修改对象,忽略空值
    * @return
    */
    int updateIgnoreNull(Personal personal);

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

    List<Personal> findOwner();

}
