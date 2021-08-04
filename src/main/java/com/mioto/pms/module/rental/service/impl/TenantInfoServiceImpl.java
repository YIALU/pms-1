package com.mioto.pms.module.rental.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.mioto.pms.exception.BasicException;
import com.mioto.pms.module.dictionary.component.SysConstant;
import com.mioto.pms.result.SystemTip;
import org.springframework.stereotype.Service;

import com.mioto.pms.module.rental.dao.TenantInfoDao;
import com.mioto.pms.module.rental.model.TenantInfo;
import com.mioto.pms.module.rental.service.ITenantInfoService;
import javax.annotation.Resource;
import java.util.List;

/**
 *
 * @author qinxj
 * @date 2021-06-30 14:53:10
 */
@Service("tenantInfoService")
public class TenantInfoServiceImpl implements ITenantInfoService{

    @Resource
    private TenantInfoDao tenantInfoDao;

    @Override
    public List<TenantInfo> findList(TenantInfo tenantInfo) {
        return tenantInfoDao.findList(tenantInfo);
    }

    @Override
    public int insert(TenantInfo tenantInfo) {
        return tenantInfoDao.insert(tenantInfo);
    }

    @Override
    public int insertIgnoreNull(TenantInfo tenantInfo) {
        //如果手机号码已经存在，则不添加
        TenantInfo tenant = findByColumn("phone",tenantInfo.getPhone());
        if (ObjectUtil.isEmpty(tenant)) {
            return tenantInfoDao.insertIgnoreNull(tenantInfo);
        }
        throw new BasicException(SystemTip.PHONE_REUSE);
    }

    @Override
    public int update(TenantInfo tenantInfo) {
        return tenantInfoDao.update(tenantInfo);
    }

    @Override
    public int updateIgnoreNull(TenantInfo tenantInfo) {
        return tenantInfoDao.updateIgnoreNull(tenantInfo);
    }

    @Override
    public TenantInfo findByColumn(String column, Object value) {
        return tenantInfoDao.findByColumn(column,value);
    }

    @Override
    public int deleteByColumn(String column, Object value) {
        return tenantInfoDao.deleteByColumn(column,value);
    }

    @Override
    public int batchDelete(Integer[] ids) {
        return tenantInfoDao.batchDelete(ids);
    }
}