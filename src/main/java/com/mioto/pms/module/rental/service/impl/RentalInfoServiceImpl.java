package com.mioto.pms.module.rental.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.mioto.pms.module.cost.model.CostDetailListVO;
import com.mioto.pms.module.price.PricingStrategyEnum;
import com.mioto.pms.module.price.model.Price;
import com.mioto.pms.module.price.service.PriceService;
import com.mioto.pms.module.rental.dao.RentalFileDao;
import com.mioto.pms.module.rental.dao.RentalInfoDao;
import com.mioto.pms.module.rental.model.*;
import com.mioto.pms.module.rental.service.IRentalInfoService;
import com.mioto.pms.module.rental.service.IRentalInitService;
import com.mioto.pms.module.rental.service.IRentalTenantService;
import com.mioto.pms.module.rental.service.ITenantInfoService;
import com.mioto.pms.module.room.service.RoomService;
import com.mioto.pms.utils.BaseUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    private IRentalInitService rentalInitService;
    @Resource
    private RoomService roomService;
    @Resource
    private RentalFileDao rentalFileDao;
    @Resource
    private PriceService priceService;

    @Override
    public List<RentalListVO> findList(RentalDTO rentalDTO) {
        rentalDTO.setUserId(BaseUtil.getLogonUserId());
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
        //????????????????????????
        if (CollUtil.isNotEmpty(rentalInfo.getTenantInfoList())) {
            addTenantAndRelation(id, rentalInfo.getTenantInfoList());
        }
        //????????????????????????
        if (CollUtil.isNotEmpty(rentalInfo.getRentalFileList())){
            rentalFileDao.insertRentalFile(id,rentalInfo.getRentalFileList());
        }
        //????????????????????????????????????
        if (CollUtil.isNotEmpty(rentalInfo.getRentalInitList())){
            rentalInitService.insert(rentalInfo.getRentalInitList(),id);
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
        if (CollUtil.isNotEmpty(rentalInfo.getRentalInitList())){
            rentalInitService.update(rentalInfo.getRentalInitList(),rentalInfo.getId());
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
        RentalDetailVO rentalDetailVO = rentalInfoDao.findDetailById(id);
        //???????????????????????????????????????????????????
        List<String> costTypeList = roomService.findDynamicCostTypes(rentalDetailVO.getRoomId());
        if (CollUtil.isNotEmpty(costTypeList)){
            if (CollUtil.isEmpty(rentalDetailVO.getRentalInitList())){
                //??????????????????????????????????????????
                //??????????????????????????????????????????????????????????????????
                //?????????????????????????????????????????????????????????????????????
                List<RentalInit> rentalInitList = new ArrayList<>(costTypeList.size());
                costTypeList.stream().forEach(costType -> rentalInitList.add(builderRentalInit(costType)));
                rentalDetailVO.setRentalInitList(rentalInitList);
            }else {
                final Map<String,Double> map = rentalDetailVO.getRentalInitList().stream()
                        .collect(Collectors.toMap(RentalInit::getCostType,RentalInit::getInitVal));
                costTypeList.stream().forEach(costType ->{
                    if (!map.containsKey(costType)){
                        rentalDetailVO.getRentalInitList().add(builderRentalInit(costType));
                    }
                });
            }
        }
        return rentalDetailVO;
    }

    @Override
    public List<RentalInfo> findByRoomIdAndStatus(String roomId, int status) {
        return rentalInfoDao.findByRoomIdAndStatus(roomId,status);
    }

    @Override
    public CancellationVO findCancellation(String rentalId) {
        CancellationVO cancellationVO = rentalInfoDao.findCancellation(rentalId) ;
        cancellationVO.setCostDetailList(builderCostDetailList(cancellationVO.getCostDetailList(), rentalId));
        return cancellationVO;
    }

    @Override
    public WxCancellationVO findWxCancellation(String rentalId) {
        WxCancellationVO cancellationVO = rentalInfoDao.findWxCancellation(rentalId);
        cancellationVO.setCostDetailList(builderCostDetailList(cancellationVO.getCostDetailList(), rentalId));
        return cancellationVO;
    }

    @Override
    public String[] findCancelIdsByBillNumbers(String[] billNumbers) {
        return rentalInfoDao.findCancelIdsByBillNumbers(billNumbers);
    }

    @Override
    public int updateStatusBatch(String[] rentalIds, int status) {
        return rentalInfoDao.updateStatusBatch(rentalIds,status);
    }

    /**
     * ???????????????????????????
     * @param costDetailList
     * @return
     */
    private List<CostDetailListVO> builderCostDetailList(List<CostDetailListVO> costDetailList,String rentalId){
        List<CostDetailListVO> newCostDetailList = new ArrayList<>(costDetailList.size());
        CostDetailListVO newCostDetailListVO;
        /*Date currentDate = new Date();
        costDetailList = costDetailList.stream().sorted(Comparator.comparing(CostDetailListVO::getEndDate).reversed()).collect(
                collectingAndThen(toCollection(() -> new TreeSet<>(Comparator.comparing(CostDetailListVO::getCostTypeId))),
                        ArrayList::new));*/
        List<Price> priceList = priceService.findByRentalId(rentalId);
        Map<String,Price> priceMap = priceList.stream().collect(Collectors.toMap(Price::getType,Price::get));
        for (CostDetailListVO costDetailListVO : costDetailList) {
            if (PricingStrategyEnum.getInstance(costDetailListVO.getCostTypeId()).getType() == 1){
                //?????????????????? - ??????????????????????????????????????????
                newCostDetailListVO = costDetailListVO;
                newCostDetailListVO.setStartData(costDetailListVO.getEndData());
                newCostDetailListVO.setStartDate(costDetailListVO.getEndDate());
                newCostDetailListVO.setEndData(null);
                newCostDetailListVO.setEndDate(null);
                newCostDetailListVO.setAmount(null);
                newCostDetailListVO.setChildBillNumber("");
                newCostDetailListVO.setPayStatus(0);
                newCostDetailListVO.setUnit(priceMap.get(costDetailListVO.getCostTypeId()).getUnitPrice());
                newCostDetailList.add(newCostDetailListVO);
            }else if (PricingStrategyEnum.getInstance(costDetailListVO.getCostTypeId()).getType() == 2) {
                continue;
            }else {
                //?????????????????????
                if (costDetailListVO.getPayStatus() == 1){
                    continue;
                }
                newCostDetailList.add(costDetailListVO);
            }
        }
        return newCostDetailList;
    }

    /**
     * ????????????????????????????????????
     * @param rentalId
     * @param tenantInfoList
     */
    private void addTenantAndRelation(String rentalId, List<TenantInfo> tenantInfoList){
        tenantInfoList.stream().forEach(tenantInfo -> {
            //??????????????????
            if (ObjectUtil.isEmpty(tenantInfo.getId())) {
                tenantInfoService.insertIgnoreNull(tenantInfo);
            }
            //???????????????????????????
            rentalTenantService.insertIgnoreNull(new RentalTenant(rentalId,tenantInfo.getId()));
        });
    }

    private RentalInit builderRentalInit(String costType){
        RentalInit rentalInit = new RentalInit();
        rentalInit.setCostType(costType);
        rentalInit.setInitVal(0d);
        return rentalInit;
    }
}