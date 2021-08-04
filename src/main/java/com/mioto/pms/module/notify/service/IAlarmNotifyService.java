package com.mioto.pms.module.notify.service;

import com.mioto.pms.module.notify.model.AlarmNotify;
import java.util.List;

/**
 * IAlarmNotifyService
 *
 * @author qinxj
 * @date 2021-07-14 14:43:19
 */
public interface IAlarmNotifyService{

    /**
     * 根据条件查询列表
     * @param alarmNotify
     * @return
     */
    List<AlarmNotify> findList(AlarmNotify alarmNotify);

    /**
     * 根据列名和对应的值查询对象
     * @param column
     * @param value
     * @return
     */
    AlarmNotify findByColumn(String column, Object value);

    /**
     * 新增对象
     * @param alarmNotify
     * @return
     */
    int insert(AlarmNotify alarmNotify);

    /**
     * 新增对象,忽略空值
     * @param alarmNotify
     * @return
     */
    int insertIgnoreNull(AlarmNotify alarmNotify);

    /**
     * 修改对象
     * @param alarmNotify
     * @return
     */
    int update(AlarmNotify alarmNotify);

    /**
     * 修改对象,忽略空值
     * @param alarmNotify
     * @return
     */
    int updateIgnoreNull(AlarmNotify alarmNotify);

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

    AlarmNotify find();
}