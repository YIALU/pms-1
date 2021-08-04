package com.mioto.pms.module.arrears.service.impl;

import cn.hutool.core.util.IdUtil;
import com.mioto.pms.module.arrears.dao.ArrearsDao;
import com.mioto.pms.module.arrears.model.Arrears;
import com.mioto.pms.module.arrears.service.ArrearsService;
import com.mioto.pms.utils.BaseUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
* @auther lzc
* date 2021-04-28 15:31:26
*/
@Service("arrearsService")
public class ArrearsServiceImpl implements ArrearsService {

    @Resource
    private ArrearsDao arrearsDao;

    /**
    * 根据条件查询列表
    */
    @Override
    public List<Arrears> findList(Arrears arrears) {
        if (BaseUtil.getLoginUserRoleId() == 2){
            arrears.setUserId(BaseUtil.getLoginUser().getId());
        }
        return arrearsDao.findList(arrears);
    }

    /**
    * 根据列名和对应的值查询对象
    */
    @Override
    public Arrears findByColumn(String column, String value) {
        return arrearsDao.findByColumn(column, value);
    }

    /**
    * 新增对象,忽略空值
    */
    @Override
    public int insertIgnoreNull(Arrears arrears) {
        arrears.setUserId(BaseUtil.getLoginUser().getId());
        return arrearsDao.insertIgnoreNull(arrears);
    }

    /**
    * 修改对象,忽略空值
    */
    @Override
    public int updateIgnoreNull(Arrears arrears) {
        return arrearsDao.updateIgnoreNull(arrears);
    }

    /**
    * 根据列名和对应的值删除对象
    */
    @Override
    public int deleteByColumn(String column, String value) {
        return arrearsDao.deleteByColumn(column,value);
    }

    /**
    * 根据主键列表批量删除
    */
    @Override
    public int batchDelete(String[] ids) {
        return arrearsDao.batchDelete(ids);
    }

}
