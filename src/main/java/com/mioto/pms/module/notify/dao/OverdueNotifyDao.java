package com.mioto.pms.module.notify.dao;

import com.mioto.pms.module.notify.model.OverdueNotify;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 *
 * @author qinxj
 * @date 2021-07-14 14:43:18
 */
@Mapper
public interface OverdueNotifyDao{

    /**
     * 根据条件查询列表
     * @param overdueNotify
     * @return
     */
    List<OverdueNotify> findList(OverdueNotify overdueNotify);

    /**
     * 根据列名和对应的值查询对象
     * @param column
     * @param value
     * @return
     */
    OverdueNotify findByColumn(@Param("column") String column,@Param("value") Object value);

    /**
     * 新增对象
     * @param overdueNotify
     * @return
     */
    int insert(OverdueNotify overdueNotify);

    /**
     * 新增对象,忽略空值
     * @param overdueNotify
     * @return
     */
    int insertIgnoreNull(OverdueNotify overdueNotify);

    /**
     * 修改对象
     * @param overdueNotify
     * @return
     */
    int update(OverdueNotify overdueNotify);

    /**
     * 修改对象,忽略空值
     * @param overdueNotify
     * @return
     */
    int updateIgnoreNull(OverdueNotify overdueNotify);

    /**
     * 根据列名和对应的值删除对象
     * @param column
     * @param value
     * @return
     */
    int deleteByColumn(@Param("column") String column,@Param("value") Object value);

    /**
     * 根据主键列表批量删除
     * @param ids
     * @return
     */
    int batchDelete(Integer[] ids);

    OverdueNotify find();
}