package com.mioto.pms.module.rental.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.mioto.pms.module.rental.dao.RentalInitDao;
import com.mioto.pms.module.rental.model.RentalInit;
import com.mioto.pms.module.rental.service.IRentalInitService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author admin
 * @date 2021-08-14 14:48
 */
@Service
public class RentalInitServiceImpl implements IRentalInitService {
    @Resource
    private RentalInitDao rentalInitDao;

    @Override
    public int insert(List<RentalInit> rentalInitList,String rentalId) {
        Date now = new Date();
        for (RentalInit rentalInit : rentalInitList) {
            rentalInit.setCreateTime(now);
            rentalInit.setRentalId(rentalId);
        }
        return rentalInitDao.insert(rentalInitList);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int update(List<RentalInit> rentalInitList, String rentalId) {
        Date now = new Date();
        List<RentalInit> newRentalInitList = new ArrayList<>(rentalInitList.size());
        List<RentalInit> oldRentalInitList = new ArrayList<>(rentalInitList.size());
        for (RentalInit rentalInit : rentalInitList) {
            //如果id为空，说明是房屋新增了收费策略
            if (ObjectUtil.isEmpty(rentalInit.getId())){
                rentalInit.setCreateTime(now);
                rentalInit.setRentalId(rentalId);
                newRentalInitList.add(rentalInit);
            }else {
                oldRentalInitList.add(rentalInit);
            }
        }

        if (CollUtil.isNotEmpty(newRentalInitList)){
            rentalInitDao.insert(newRentalInitList);
        }
        if (CollUtil.isNotEmpty(oldRentalInitList)){
            rentalInitDao.update(oldRentalInitList);
        }
        return 1;
    }

    @Override
    public Double findInitVal(String costType, String rentalId) {
        return rentalInitDao.findInitVal(costType,rentalId);
    }
}
