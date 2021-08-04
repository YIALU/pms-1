package com.mioto.pms.module.user.dao;

import com.mioto.pms.module.user.model.Function;
import com.mioto.pms.module.user.model.FunctionVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* @auther lzc
* date 2021-04-01 16:24:10
*/
@Repository
public interface FunctionDao {

    /**
    * 根据条件查询列表
    * @return
    */
    List<Function> findList(Function function);

    /**
    * 根据列名和对应的值查询对象
    * @param column
    * @param value
    * @return
    */
    Function findByColumn(@Param("column") String column, @Param("value") String value);

    /**
    * 新增对象,忽略空值
    * @return
    */
    int insertIgnoreNull(Function function);

    /**
    * 修改对象,忽略空值
    * @return
    */
    int updateIgnoreNull(Function function);

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

    List<FunctionVO> findByRoleId(Integer roleId);

    int delPermissions(Integer roleId);

    int insertPermissions(@Param("roleId")Integer roleId,@Param("functionIds")Integer[] functions);
}
