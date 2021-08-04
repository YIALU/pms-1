package com.mioto.pms.module.cost.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.mioto.pms.module.cost.model.*;
import com.mioto.pms.module.cost.service.ICostDetailService;
import com.mioto.pms.module.meter.dao.MeterReadingDao;
import com.mioto.pms.module.meter.model.MeterReading;
import com.mioto.pms.module.price.model.Price;
import com.mioto.pms.module.price.PricingStrategyEnum;
import com.mioto.pms.module.price.service.PriceService;
import com.mioto.pms.module.rental.model.RentalInfo;
import com.mioto.pms.module.rental.service.IRentalInfoService;
import com.mioto.pms.utils.BaseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import com.mioto.pms.module.cost.dao.CostInfoDao;
import com.mioto.pms.module.cost.service.ICostInfoService;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
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
    public List<CostListVO> findCostList(CostListDTO costListDTO) {
        if (BaseUtil.getLoginUserRoleId() == 2){
            costListDTO.setUserId(BaseUtil.getLoginUser().getId());
        }
        return costInfoDao.findCostList(costListDTO);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void insertDetail(String roomId,boolean firstInsert) {
        List<RentalInfo> rentalInfoList = rentalInfoService.findByRoomIdAndStatus(roomId,1);
        if (CollUtil.isNotEmpty(rentalInfoList) && rentalInfoList.size() == 1) {
            if (firstInsert){
                firstInsertCostInfo(rentalInfoList.get(0));
                return;
            }
            insertCostInfo(rentalInfoList.get(0));
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

    private void firstInsertCostInfo(RentalInfo rentalInfo){
        log.info("租住办理成功，开始生成账单信息");
        //创建总账单
        CostInfo costInfo = builderCostInfo(rentalInfo.getId());
        //账单创建时间为租住办理时间
        costInfo.setCreateTime(rentalInfo.getCreateDate());
        //费用总金额
        double totalAmount = 0d;
        List<Price> priceList = priceService.findByRoomId(rentalInfo.getRoomId());
        //查询抄表时间
        MeterReading meterReading = meterReadingDao.findByRoomId(rentalInfo.getRoomId());

        if (CollUtil.isNotEmpty(priceList) && ObjectUtil.isNotEmpty(meterReading)){
            //子账单计数器
            int index = 1;
            //当月天数
            int dayOfMonth = 30;
            //当月抄表时间
            Date meterReadTime = BaseUtil.toDate(meterReading.getDate(),meterReading.getTime());
            //结束时间默认为本月抄表时间
            Date end =  meterReadTime;
            //是否为抄表当天入住
            boolean isNow = false;
            //如果租住办理时间大于等于本月抄表时间，结束时间为下次抄表时间
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
                        double amount = NumberUtil.round(NumberUtil.mul(price.getUnitPrice() / dayOfMonth, BaseUtil.intervalDay(costInfo.getCreateTime(), end)), 2).doubleValue();
                        totalAmount = NumberUtil.add(totalAmount, amount);
                        costDetail.setAmount(amount);
                    }
                    costDetailService.insertIgnoreNull(costDetail);
                    index++;
                    log.info("子账单生成成功,账单类型 - {},账单金额 - {},账单编号 - {}",pricingStrategyEnum.name(),costDetail.getAmount(),costDetail.getBillChildNumber());
                }
            }
        }

        costInfo.setTotalAmount(totalAmount);
        costInfoDao.insertIgnoreNull(costInfo);
        log.info("主账单生成成功,账单总金额 - {},账单编号 - {}",totalAmount,costInfo.getBillNumber());
    }

    /**
     * 抄表指令发送成功后，生成账单信息
     * @param rentalInfo
     */
    private void insertCostInfo(RentalInfo rentalInfo){
        log.info("抄表指令发送成功，开始生成账单信息");
        //创建总账单
        CostInfo costInfo = builderCostInfo(rentalInfo.getId());
        List<Price> priceList = priceService.findByRoomId(rentalInfo.getRoomId());
        if (CollUtil.isNotEmpty(priceList)){
            //查询抄表时间
            MeterReading meterReading = meterReadingDao.findByRoomId(rentalInfo.getRoomId());
            if (ObjectUtil.isNotEmpty(meterReading)){
                costInfo.setCreateTime(BaseUtil.toDate(meterReading.getDate(),meterReading.getTime()));
                //子账单计数器
                int index = 1;
                //查询最新一条历史账单和动态费用明细
                CostDetailBO costDetailBO = costInfoDao.findLastDynamicCost(costInfo.getRentalId());
                List<CostDetail> costDetailList = costDetailBO.getCostDetailList();
                for (Price price : priceList) {
                    CostDetail costDetail = builderCostDetail(costInfo,price,index);
                    PricingStrategyEnum pricingStrategyEnum = PricingStrategyEnum.getInstance(price.getType());
                    if (pricingStrategyEnum.getType() == 2){
                        costDetail.setCostStartTime(costInfo.getCreateTime());
                        costDetail.setCostEndTime(DateUtil.offsetMonth(costInfo.getCreateTime(),1));
                        costDetail.setAmount(price.getUnitPrice());
                    }else {
                        costDetail.setCostStartTime(costDetailBO.getCreateTime());
                        costDetail.setCostEndTime(costInfo.getCreateTime());
                        //租住办理后，如果没有自动抄表，取租住办理时的初始数据
                        //如果有抄表账单数据，取上一次抄表数据
                        for (CostDetail detail : costDetailList) {
                            if (StrUtil.equals(price.getType(),detail.getCostType())){
                                costDetail.setCostStartData(detail.getCostEndData());
                                break;
                            }
                        }
                        //说明入住后，还没有执行过抄表策略
                        if (ObjectUtil.isEmpty(costDetail.getCostStartData())){
                            costDetail.setCostStartData(getInitVal(price.getType(),rentalInfo));
                        }
                    }
                    index++;
                    costDetailService.insertIgnoreNull(costDetail);
                    log.info("子账单生成成功,账单类型 - {},账单金额 - {},账单编号 - {}",pricingStrategyEnum.name(),costDetail.getAmount(),costDetail.getBillChildNumber());
                }
            }
        }
        costInfoDao.insertIgnoreNull(costInfo);
        log.info("主账单生成成功,账单编号 - {}",costInfo.getBillNumber());
    }

    /**
     * 获取入住设备初始值
     * @param priceType 收费类型
     * @param rentalInfo 入住对象
     * @return
     */
    private double getInitVal(String priceType,RentalInfo rentalInfo){
        String val = "0";
        if (StrUtil.equals(priceType,PricingStrategyEnum.GAS_FEE.getId())){
            val =  rentalInfo.getCoalGasVal();
        }else if (StrUtil.equals(priceType,PricingStrategyEnum.ELECTRICITY_FEE.getId())){
            val =  rentalInfo.getElectricityMeterVal();
        }else if (StrUtil.equals(priceType,PricingStrategyEnum.WATER_FEE.getId())){
            val =  rentalInfo.getWaterMeterVal();
        }
       return Double.parseDouble(val);
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
}