package com.mioto.pms.module.notify.service;

import com.mioto.pms.module.notify.model.OverdueNotify;
import java.util.List;

/**
 * IOverdueNotifyService
 *
 * @author qinxj
 * @date 2021-07-14 14:43:18
 */
public interface IOverdueNotifyService{

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
    OverdueNotify findByColumn(String column, Object value);

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
    int deleteByColumn(String column, Object value);

    /**
     * 根据主键列表批量删除
     * @param ids
     * @return
     */
    int batchDelete(Integer[] ids);

    OverdueNotify find();
}