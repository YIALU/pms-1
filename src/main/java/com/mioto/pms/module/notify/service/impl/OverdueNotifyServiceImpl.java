package com.mioto.pms.module.notify.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.mioto.pms.module.notify.model.OverdueNotifyBO;
import org.springframework.stereotype.Service;

import com.mioto.pms.module.notify.dao.OverdueNotifyDao;
import com.mioto.pms.module.notify.model.OverdueNotify;
import com.mioto.pms.module.notify.service.IOverdueNotifyService;
import javax.annotation.Resource;
import java.util.List;

/**
 *
 * @author qinxj
 * @date 2021-07-14 14:43:18
 */
@Service("overdueNotifyService")
public class OverdueNotifyServiceImpl implements IOverdueNotifyService{

    @Resource
    private OverdueNotifyDao overdueNotifyDao;

    @Override
    public List<OverdueNotify> findList(OverdueNotify overdueNotify) {
        return overdueNotifyDao.findList(overdueNotify);
    }

    @Override
    public int insert(OverdueNotify overdueNotify) {
        return overdueNotifyDao.insert(overdueNotify);
    }

    @Override
    public int insertIgnoreNull(OverdueNotify overdueNotify) {
        return overdueNotifyDao.insertIgnoreNull(overdueNotify);
    }

    @Override
    public int update(OverdueNotify overdueNotify) {
        return overdueNotifyDao.update(overdueNotify);
    }

    @Override
    public int updateIgnoreNull(OverdueNotify overdueNotify) {
        if (ObjectUtil.isNotEmpty(overdueNotify) && ObjectUtil.isNotEmpty(overdueNotify.getId())) {
            return overdueNotifyDao.updateIgnoreNull(overdueNotify);
        }
        return overdueNotifyDao.insertIgnoreNull(overdueNotify);
    }

    @Override
    public OverdueNotify findByColumn(String column, Object value) {
        return overdueNotifyDao.findByColumn(column,value);
    }

    @Override
    public int deleteByColumn(String column, Object value) {
        return overdueNotifyDao.deleteByColumn(column,value);
    }

    @Override
    public int batchDelete(Integer[] ids) {
        return overdueNotifyDao.batchDelete(ids);
    }

    @Override
    public OverdueNotify find() {
        OverdueNotify overdueNotify = overdueNotifyDao.find();
        if (ObjectUtil.isEmpty(overdueNotify)){
            return new OverdueNotify();
        }
        return overdueNotify;
    }

    @Override
    public List<OverdueNotifyBO> findByCostInfoIds(String[] costInfoIds) {
        return overdueNotifyDao.findByCostInfoIds(costInfoIds);
    }
}