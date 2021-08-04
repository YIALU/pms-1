package com.mioto.pms.module.notify.service.impl;

import cn.hutool.core.util.ObjectUtil;
import org.springframework.stereotype.Service;

import com.mioto.pms.module.notify.dao.AlarmNotifyDao;
import com.mioto.pms.module.notify.model.AlarmNotify;
import com.mioto.pms.module.notify.service.IAlarmNotifyService;
import javax.annotation.Resource;
import java.util.List;

/**
 *
 * @author qinxj
 * @date 2021-07-14 14:43:19
 */
@Service("alarmNotifyService")
public class AlarmNotifyServiceImpl implements IAlarmNotifyService{

    @Resource
    private AlarmNotifyDao alarmNotifyDao;

    @Override
    public List<AlarmNotify> findList(AlarmNotify alarmNotify) {
        return alarmNotifyDao.findList(alarmNotify);
    }

    @Override
    public int insert(AlarmNotify alarmNotify) {
        return alarmNotifyDao.insert(alarmNotify);
    }

    @Override
    public int insertIgnoreNull(AlarmNotify alarmNotify) {
        return alarmNotifyDao.insertIgnoreNull(alarmNotify);
    }

    @Override
    public int update(AlarmNotify alarmNotify) {
        return alarmNotifyDao.update(alarmNotify);
    }

    @Override
    public int updateIgnoreNull(AlarmNotify alarmNotify) {
        if (ObjectUtil.isNotEmpty(alarmNotify) && ObjectUtil.isNotEmpty(alarmNotify.getId())) {
            return alarmNotifyDao.updateIgnoreNull(alarmNotify);
        }
        return insertIgnoreNull(alarmNotify);
    }

    @Override
    public AlarmNotify findByColumn(String column, Object value) {
        return alarmNotifyDao.findByColumn(column,value);
    }

    @Override
    public int deleteByColumn(String column, Object value) {
        return alarmNotifyDao.deleteByColumn(column,value);
    }

    @Override
    public int batchDelete(Integer[] ids) {
        return alarmNotifyDao.batchDelete(ids);
    }

    @Override
    public AlarmNotify find() {
        AlarmNotify alarmNotify = alarmNotifyDao.find();
        if (ObjectUtil.isNotEmpty(alarmNotify)){
            return alarmNotify;
        }

        return new AlarmNotify();
    }
}