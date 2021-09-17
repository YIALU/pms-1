package com.mioto.pms.module.cost.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.mioto.pms.module.cost.dao.CostTypeDao;
import com.mioto.pms.module.cost.model.CostType;
import com.mioto.pms.module.cost.service.ICostTypeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author admin
 * @date 2021-08-28 15:08
 */
@Service
public class CostTypeService implements ICostTypeService {

    @Resource
    private CostTypeDao costTypeDao;

    @Override
    public CostType add(String name) {
        name = StrUtil.trim(name);
        CostType costType = costTypeDao.find(name);
        if (ObjectUtil.isEmpty(costType)){
            costType = new CostType();
            costType.setId(IdUtil.simpleUUID());
            costType.setName(name);
            costTypeDao.add(costType);
        }
        return costType;
    }
}
