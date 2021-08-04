package com.mioto.pms.module.arrears.dao;

import com.mioto.pms.module.arrears.model.Arrears;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* @auther lzc
* date 2021-04-28 15:31:26
*/
@Repository
public interface ArrearsDao {

    /**
    * 根据条件查询列表
    * @return
    */
    List<Arrears> findList(Arrears arrears);

    /**
    * 根据列名和对应的值查询对象
    * @param column
    * @param value
    * @return
    */
    Arrears findByColumn(@Param("column") String column, @Param("value") String value);

    /**
    * 新增对象,忽略空值
    * @return
    */
    int insertIgnoreNull(Arrears arrears);

    /**
    * 修改对象,忽略空值
    * @return
    */
    int updateIgnoreNull(Arrears arrears);

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
}
