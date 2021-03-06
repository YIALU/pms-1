package com.mioto.pms.module.cost.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.mioto.pms.exception.BasicException;
import com.mioto.pms.module.cost.model.*;
import com.mioto.pms.module.cost.service.ICostDetailService;
import com.mioto.pms.module.meter.dao.MeterReadingDao;
import com.mioto.pms.module.meter.model.MeterReading;
import com.mioto.pms.module.price.model.Price;
import com.mioto.pms.module.price.PricingStrategyEnum;
import com.mioto.pms.module.price.service.PriceService;
import com.mioto.pms.module.rental.model.RentalInfo;
import com.mioto.pms.module.rental.service.IRentalInfoService;
import com.mioto.pms.module.rental.service.IRentalInitService;
import com.mioto.pms.module.weixin.model.ManualMeterReadDTO;
import com.mioto.pms.result.SystemTip;
import com.mioto.pms.utils.BaseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import com.mioto.pms.module.cost.dao.CostInfoDao;
import com.mioto.pms.module.cost.service.ICostInfoService;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 *
 * @author qinxj
 * @date 2021-07-16 15:05:11
 */
@Service("costInfoService")
@Slf4j
public class CostInfoServiceImpl implements ICostInfoService{

    @Resource
    private CostInfoDao costInfoDao;
    @Resource
    private IRentalInfoService rentalInfoService;
    @Resource
    private PriceService priceService;
    @Resource
    private ICostDetailService costDetailService;
    @Resource
    private MeterReadingDao meterReadingDao;
    @Resource
    private IRentalInitService rentalInitService;

    @Override
    public List<CostInfo> findList(CostInfo costInfo) {
        return costInfoDao.findList(costInfo);
    }

    @Override
    public int insert(CostInfo costInfo) {
        return costInfoDao.insert(costInfo);
    }


    @Override
    public int insertIgnoreNull(CostInfo costInfo) {
        return costInfoDao.insertIgnoreNull(costInfo);
    }

    @Override
    public int update(CostInfo costInfo) {
        return costInfoDao.update(costInfo);
    }

    @Override
    public int updateIgnoreNull(CostInfo costInfo) {
        return costInfoDao.updateIgnoreNull(costInfo);
    }

    @Override
    public CostInfo findByColumn(String column, Object value) {
        return costInfoDao.findByColumn(column,value);
    }

    @Override
    public int deleteByColumn(String column, Object value) {
        return costInfoDao.deleteByColumn(column,value);
    }

    @Override
    public int batchDelete(Integer[] ids) {
        return costInfoDao.batchDelete(ids);
    }

    @Override
    public List<CostListVO> findCostList(CostListDTO costListDTO,boolean isALl) {
        costListDTO.setUserId(BaseUtil.getLogonUserId());
        List<CostListVO> costListVOList = costInfoDao.findCostList(costListDTO);
        if (CollUtil.isNotEmpty(costListVOList)){
            for (CostListVO costListVO : costListVOList) {
                List<CostDetailListVO> costDetailListVOList = costListVO.getCostDetailListVOList();
                if (!isALl) {
                    List<CostDetailListVO> newCostDetailList = new ArrayList<>(3);
                    if (CollUtil.isNotEmpty(costDetailListVOList)){
                        for (CostDetailListVO costDetailListVO : costDetailListVOList) {

                                //??????????????????????????????????????????
                                if (StrUtil.equals(costDetailListVO.getCostTypeId(), PricingStrategyEnum.RENT_FEE.getId())
                                        || StrUtil.equals(costDetailListVO.getCostTypeId(), PricingStrategyEnum.WATER_FEE.getId())
                                        || StrUtil.equals(costDetailListVO.getCostTypeId(), PricingStrategyEnum.ELECTRICITY_FEE.getId())) {
                                    newCostDetailList.add(costDetailListVO);
                                }

                        }
                        costListVO.setCostDetailListVOList(newCostDetailList);
                    }
                }
            }
        }
        return costListVOList;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void insertDetail(String roomId) {
        List<RentalInfo> rentalInfoList = rentalInfoService.findByRoomIdAndStatus(roomId,1);
        if (CollUtil.isNotEmpty(rentalInfoList) && rentalInfoList.size() == 1) {
           firstInsertCostInfo(rentalInfoList.get(0));
        }
    }

    @Override
    public int batchSend(String[] costInfoIds) {
        return costInfoDao.batchSend(costInfoIds);
    }

    @Override
    public CostDetailVO findDetail(String costInfoId) {
        return costInfoDao.findDetail(costInfoId);
    }

    @Override
    public int batchChangePayStatus(String[] billNumber) {
        return costInfoDao.batchChangePayStatus(billNumber);
    }

    @Override
    public List<CostInfo> findByBillNumbers(String[] billNumbers) {
        return costInfoDao.findByBillNumbers(billNumbers);
    }

    @Override
    public int updateAmount(String id) {
        return costInfoDao.updateAmount(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int manualMeterRead(ManualMeterReadDTO meterReadDTOList) {
        log.info("???????????????????????????????????????????????????");
        CostInfo costInfo;
        //?????????????????????????????????
        Date currentDate = new Date();
        double totalAmount = 0d;
        //??????????????????????????????
        CostDetailBO costDetailBO = costInfoDao.findLastCost(meterReadDTOList.getRentalId());
        Date lastFixTypeEndTime = getLastEndTimeByFixType(costDetailBO.getCostDetailList());
        //???????????????????????????
        //??????????????????????????????2021-08-10 - 2021-09-10 ?????????????????????????????????2021-09-10????????????????????????
        boolean isGenMainBill = DateUtil.beginOfDay(currentDate).getTime() >= DateUtil.beginOfDay(lastFixTypeEndTime).getTime();
        if (isGenMainBill) {
            costInfo = builderCostInfo(meterReadDTOList.getRentalId());
            costInfo.setCreateTime(currentDate);
        }else {
            costInfo = costDetailBO;
            costInfo.setPayStatus(0);
            totalAmount = costInfo.getTotalAmount();
        }
        RentalInfo rentalInfo = rentalInfoService.findByColumn("id",meterReadDTOList.getRentalId());
        List<Price> priceList = priceService.findByRoomId(rentalInfo.getRoomId());
        int index = 1;
        int lastCostDetailSize = costDetailBO.getCostDetailList().size() + 1;
        for (Price price : priceList) {
            CostDetail costDetail;
            PricingStrategyEnum pricingStrategyEnum = PricingStrategyEnum.getInstance(price.getType());
            CostDetail lastCostDetail  = getCostDetailByType(price.getType(),costDetailBO.getCostDetailList());
            if (pricingStrategyEnum.getType() == 2){
                //????????????
                //?????????????????????????????????????????????????????????????????????????????????????????????????????????????????????,???????????????????????????????????????????????????
                if (isGenMainBill){
                    costDetail = builderCostDetail(costInfo,price,index);
                }else if (ObjectUtil.isEmpty(lastCostDetail)){
                    costDetail = builderCostDetail(costInfo,price,lastCostDetailSize);
                    lastCostDetailSize++;
                }else {
                    continue;
                }
                costDetail.setCostStartTime(lastFixTypeEndTime);
                costDetail.setCostEndTime(DateUtil.offsetMonth(lastFixTypeEndTime, 1));
                costDetail.setAmount(price.getUnitPrice());
                index++;
            }else {
                //??????????????????
                if (meterReadDTOList.getCostMap().containsKey(price.getType())) {
                    if (isGenMainBill) {
                        costDetail = builderCostDetail(costInfo,price,index);
                    }else {
                        costDetail = builderCostDetail(costInfo,price,lastCostDetailSize);
                        lastCostDetailSize++;
                    }
                    //????????????????????????????????????????????????????????????????????????
                    //????????????????????????????????????????????????
                    if (ObjectUtil.isEmpty(lastCostDetail)) {
                        costDetail.setCostStartTime(costDetailBO.getCreateTime());
                        costDetail.setCostStartData(rentalInitService.findInitVal(price.getType(), rentalInfo.getId()));
                    }else {
                        //??????????????????????????????????????????????????????
                        costDetail.setCostStartTime(lastCostDetail.getCostEndTime());
                        costDetail.setCostStartData(lastCostDetail.getCostEndData());
                    }
                    costDetail.setCostEndTime(currentDate);
                    costDetail.setCostEndData(meterReadDTOList.getCostMap().get(price.getType()));
                    Double intervalData = NumberUtil.sub(costDetail.getCostEndData(), costDetail.getCostStartData());
                    costDetail.setAmount(NumberUtil.round(NumberUtil.mul(price.getUnitPrice(), intervalData), 2).doubleValue());
                    index++;
                }else {
                    continue;
                }


            }
            costDetailService.insertIgnoreNull(costDetail);
            log.info("?????????????????????,???????????? - {}",costDetail);
            totalAmount = NumberUtil.add(totalAmount, costDetail.getAmount().doubleValue());
        }
        costInfo.setTotalAmount(totalAmount);
        if (isGenMainBill){
            costInfoDao.insertIgnoreNull(costInfo);
        }else {
            costInfoDao.updateIgnoreNull(costInfo);
        }
        log.info("?????????????????????,???????????? - {}",costInfo);
        log.info("?????????????????????????????????????????????");
        return 1;
    }

    private void firstInsertCostInfo(RentalInfo rentalInfo){
        log.info("?????????????????????????????????????????????");
        //???????????????
        CostInfo costInfo = builderCostInfo(rentalInfo.getId());
        //???????????????????????????????????????
        costInfo.setCreateTime(rentalInfo.getCreateDate());
        //???????????????
        Double totalAmount = 0d;
        List<Price> priceList = priceService.findByRoomId(rentalInfo.getRoomId());
        //??????????????????
        MeterReading meterReading = meterReadingDao.findByRoomId(rentalInfo.getRoomId());

        if (CollUtil.isNotEmpty(priceList) && ObjectUtil.isNotEmpty(meterReading)){
            //??????????????????
            int index = 1;
            //????????????
            int dayOfMonth = 30;
            //??????????????????
            Date meterReadTime = BaseUtil.toDate(meterReading.getDate(),meterReading.getTime());
            //???????????????????????????????????????
            Date end =  meterReadTime;
            //???????????????????????????
            boolean isNow = false;
            //??????????????????????????????????????????????????????????????????????????????????????????
            if (DateUtil.beginOfDay(costInfo.getCreateTime()).getTime() > DateUtil.beginOfDay(meterReadTime).getTime()){
                end =  DateUtil.offsetMonth(meterReadTime,1);
            }else if(DateUtil.beginOfDay(costInfo.getCreateTime()).getTime() == DateUtil.beginOfDay(meterReadTime).getTime()){
                end =  DateUtil.offsetMonth(meterReadTime,1);
                isNow = true;
            }else {
                dayOfMonth = DateUtil.getEndValue(Calendar.getInstance(),Calendar.DAY_OF_MONTH);
            }
            for (Price price : priceList) {
                PricingStrategyEnum pricingStrategyEnum = PricingStrategyEnum.getInstance(price.getType());
                if (pricingStrategyEnum.getType() == 2){
                    CostDetail costDetail = builderCostDetail(costInfo,price,index);
                    costDetail.setCostStartTime(costInfo.getCreateTime());
                    costDetail.setCostEndTime(end);
                    if (isNow){
                        totalAmount = NumberUtil.add(totalAmount, price.getUnitPrice());
                        costDetail.setAmount(price.getUnitPrice());
                    }else {
                        Double amount = NumberUtil.round(NumberUtil.mul(price.getUnitPrice() / dayOfMonth, BaseUtil.intervalDay(costInfo.getCreateTime(), end)), 2).doubleValue();
                        totalAmount = NumberUtil.add(totalAmount, amount);
                        costDetail.setAmount(amount);
                    }
                    costDetailService.insertIgnoreNull(costDetail);
                    index++;
                    log.info("?????????????????????,???????????? - {},???????????? - {},???????????? - {}",pricingStrategyEnum.name(),costDetail.getAmount(),costDetail.getBillChildNumber());
                }
            }
            costInfo.setTotalAmount(totalAmount);
            costInfoDao.insertIgnoreNull(costInfo);
            log.info("?????????????????????,??????????????? - {},???????????? - {}",totalAmount,costInfo.getBillNumber());
            return;
        }
        throw new BasicException(SystemTip.PRICE_METER_EMPTY);
    }

    private CostInfo builderCostInfo(String rentalId){
        CostInfo costInfo = new CostInfo();
        costInfo.setRentalId(rentalId);
        costInfo.setBillNumber(BaseUtil.createBillNumber());
        costInfo.setId(IdUtil.simpleUUID());
        return costInfo;
    }

    private CostDetail builderCostDetail(CostInfo costInfo,Price price,int index){
        CostDetail costDetail = new CostDetail();
        costDetail.setCostInfoId(costInfo.getId());
        costDetail.setBillChildNumber(costInfo.getBillNumber() + StrUtil.DASHED + index);
        costDetail.setUnit(price.getUnitPrice());
        costDetail.setCostType(price.getType());
        costDetail.setType(ICostDetailService.BILL_TYPE_GEN);
        return costDetail;
    }

    /**
     * ???????????????????????????????????????????????????
     * @param costDetailList
     * @return
     */
    private Date getLastEndTimeByFixType(List<CostDetail> costDetailList){
        for (CostDetail detail : costDetailList) {
            if (PricingStrategyEnum.getInstance(detail.getCostType()).getType() == 2){
                return detail.getCostEndTime();
            }
        }
        return null;
    }

    private CostDetail getCostDetailByType(String type,List<CostDetail> costDetailList){
        CostDetail costDetail = null;
        for (CostDetail detail : costDetailList) {
            if (StrUtil.equals(type, detail.getCostType())) {
                if (ObjectUtil.isEmpty(costDetail)){
                    costDetail = detail;
                }else {
                    if (costDetail.getCostEndData() < detail.getCostEndData()){
                        costDetail = detail;
                    }
                }
            }
        }
        return costDetail;
    }
}