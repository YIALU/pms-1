package com.mioto.pms.module.rental.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.IdUtil;
import com.mioto.pms.anno.MeterReadingAnno;
import com.mioto.pms.module.cost.model.CostDetailListVO;
import com.mioto.pms.module.meter.MeterReadType;
import com.mioto.pms.module.price.PricingStrategyEnum;
import com.mioto.pms.module.rental.dao.RentalFileDao;
import com.mioto.pms.module.rental.model.*;
import com.mioto.pms.module.rental.service.IRentalTenantService;
import com.mioto.pms.module.rental.service.ITenantInfoService;
import com.mioto.pms.utils.BaseUtil;
import org.springframework.stereotype.Service;

import com.mioto.pms.module.rental.dao.RentalInfoDao;
import com.mioto.pms.module.rental.service.IRentalInfoService;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author qinxj
 * @date 2021-06-30 14:53:11
 */
@Service("rentalInfoService")
public class RentalInfoServiceImpl implements IRentalInfoService{

    @Resource
    private RentalInfoDao rentalInfoDao;
    @Resource
    private ITenantInfoService tenantInfoService;
    @Resource
    private IRentalTenantService rentalTenantService;
    @Resource
    private RentalFileDao rentalFileDao;

    @Override
    public List<RentalListVO> findList(RentalDTO rentalDTO) {
        if (BaseUtil.getLoginUserRoleId() == 2){
            rentalDTO.setUserId(BaseUtil.getLoginUser().getId());
        }
        return rentalInfoDao.findList(rentalDTO);
    }

    @Override
    public int insert(RentalInfo rentalInfo) {
        return rentalInfoDao.insert(rentalInfo);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int insertIgnoreNull(RentalDetailDTO rentalInfo) {
        final String id = IdUtil.simpleUUID();
        rentalInfo.setId(id);
        rentalInfo.setCreateDate(new Date());
        if (CollUtil.isNotEmpty(rentalInfo.getTenantInfoList())) {
            addTenantAndRelation(id, rentalInfo.getTenantInfoList());
        }
        if (CollUtil.isNotEmpty(rentalInfo.getRentalFileList())){
            rentalFileDao.insertRentalFile(id,rentalInfo.getRentalFileList());
        }
        return rentalInfoDao.insertIgnoreNull(rentalInfo);
    }

    @Override
    public int update(RentalInfo rentalInfo) {
        return rentalInfoDao.update(rentalInfo);
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public int updateIgnoreNull(RentalDetailDTO rentalInfo) {
        int result = 1;
        if (!BaseUtil.checkObjIsNullIgnoreId(rentalInfo,true)){
            result = rentalInfoDao.updateIgnoreNull(rentalInfo);
        }
        if (CollUtil.isNotEmpty(rentalInfo.getTenantInfoList())) {
            rentalTenantService.deleteByColumn("rental_id", rentalInfo.getId());
            addTenantAndRelation(rentalInfo.getId(), rentalInfo.getTenantInfoList());
        }
        if (CollUtil.isNotEmpty(rentalInfo.getRentalFileList())){
            rentalFileDao.delByRentalId(rentalInfo.getId());
            rentalFileDao.insertRentalFile(rentalInfo.getId(),rentalInfo.getRentalFileList());
        }
        return result;
    }

    @Override
    public RentalInfo findByColumn(String column, Object value) {
        return rentalInfoDao.findByColumn(column,value);
    }

    @Override
    public int deleteByColumn(String column, Object value) {
        return rentalInfoDao.deleteByColumn(column,value);
    }

    @Override
    public int batchDelete(String[] ids) {
        return rentalInfoDao.batchDelete(ids);
    }

    @Override
    public RentalDetailVO findDetailById(String id) {
        return rentalInfoDao.findDetailById(id);
    }

    @Override
    public List<RentalInfo> findByRoomIdAndStatus(String roomId, int status) {
        return rentalInfoDao.findByRoomIdAndStatus(roomId,status);
    }

    @Override
    public CancellationVO findCancellation(String rentalId) {
        CancellationVO cancellationVO = rentalInfoDao.findCancellation(rentalId) ;
        List<CostDetailListVO> costDetailList = cancellationVO.getCostDetailList();
        List<CostDetailListVO> newCostDetailList = new ArrayList<>(costDetailList.size());
        CostDetailListVO newCostDetailListVO;
        for (CostDetailListVO costDetailListVO : costDetailList) {
            if (PricingStrategyEnum.getInstance(costDetailListVO.getCostTypeId()).getType() == 2){
                newCostDetailList.add(costDetailListVO);
            }else {
                //生成虚拟账单 - 提交退租办理后，生成对应账单
                newCostDetailListVO = costDetailListVO;
                newCostDetailListVO.setStartData(costDetailListVO.getEndData());
                newCostDetailListVO.setStartDate(costDetailListVO.getEndDate());
                newCostDetailListVO.setEndData(null);
                newCostDetailListVO.setEndDate(new Date());
                newCostDetailListVO.setAmount(null);
                newCostDetailListVO.setChildBillNumber("");
                newCostDetailListVO.setPayStatus(0);
                newCostDetailList.add(newCostDetailListVO);
            }
        }
        cancellationVO.setCostDetailList(newCostDetailList);
        return cancellationVO;
    }

    /**
     * 新增租户和房屋出租的关系
     * @param rentalId
     * @param tenantInfoList
     */
    private void addTenantAndRelation(String rentalId, List<TenantInfo> tenantInfoList){
        tenantInfoList.stream().forEach(tenantInfo -> {
            //新增租户信息
            tenantInfoService.insertIgnoreNull(tenantInfo);
            //新增租户与租房关系
            rentalTenantService.insertIgnoreNull(new RentalTenant(rentalId,tenantInfo.getId()));
        });
    }
}