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

                                //列表页仅显示水费、电费、房租
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

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int manualMeterRead(ManualMeterReadDTO meterReadDTOList) {
        log.info("提交手动抄表数据，开始生成账单信息");
        CostInfo costInfo;
        //当前时间为账单生成时间
        Date currentDate = new Date();
        double totalAmount = 0d;
        //查询最近一次账单纪录
        CostDetailBO costDetailBO = costInfoDao.findLastCost(meterReadDTOList.getRentalId());
        Date lastFixTypeEndTime = getLastEndTimeByFixType(costDetailBO.getCostDetailList());
        //是否需要生成主账单
        //比如上次房租账单时间2021-08-10 - 2021-09-10 只有在当前时间大于等于2021-09-10才会再生成主账单
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
                //固定收费
                //判断当月是否已经生成过主账单，如果没有生成过，或者该收费类型也没有生成过子账单,则生成该收费类型对应的固定费用账单
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
                //按使用量计费
                if (meterReadDTOList.getCostMap().containsKey(price.getType())) {
                    if (isGenMainBill) {
                        costDetail = builderCostDetail(costInfo,price,index);
                    }else {
                        costDetail = builderCostDetail(costInfo,price,lastCostDetailSize);
                        lastCostDetailSize++;
                    }
                    //租住办理后，如果没有抄表，取租住办理时的初始数据
                    //说明入住后，还没有执行过抄表策略
                    if (ObjectUtil.isEmpty(lastCostDetail)) {
                        costDetail.setCostStartTime(costDetailBO.getCreateTime());
                        costDetail.setCostStartData(rentalInitService.findInitVal(price.getType(), rentalInfo.getId()));
                    }else {
                        //如果有抄表账单数据，取上一次抄表数据
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
            log.info("添加子账单完成,账单信息 - {}",costDetail);
            totalAmount = NumberUtil.add(totalAmount, costDetail.getAmount().doubleValue());
        }
        costInfo.setTotalAmount(totalAmount);
        if (isGenMainBill){
            costInfoDao.insertIgnoreNull(costInfo);
        }else {
            costInfoDao.updateIgnoreNull(costInfo);
        }
        log.info("添加主账单完成,账单信息 - {}",costInfo);
        log.info("手动抄表完成，生成账单信息结束");
        return 1;
    }

    private void firstInsertCostInfo(RentalInfo rentalInfo){
        log.info("租住办理成功，开始生成账单信息");
        //创建总账单
        CostInfo costInfo = builderCostInfo(rentalInfo.getId());
        //账单创建时间为租住办理时间
        costInfo.setCreateTime(rentalInfo.getCreateDate());
        //费用总金额
        Double totalAmount = 0d;
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
                        Double amount = NumberUtil.round(NumberUtil.mul(price.getUnitPrice() / dayOfMonth, BaseUtil.intervalDay(costInfo.getCreateTime(), end)), 2).doubleValue();
                        totalAmount = NumberUtil.add(totalAmount, amount);
                        costDetail.setAmount(amount);
                    }
                    costDetailService.insertIgnoreNull(costDetail);
                    index++;
                    log.info("子账单生成成功,账单类型 - {},账单金额 - {},账单编号 - {}",pricingStrategyEnum.name(),costDetail.getAmount(),costDetail.getBillChildNumber());
                }
            }
            costInfo.setTotalAmount(totalAmount);
            costInfoDao.insertIgnoreNull(costInfo);
            log.info("主账单生成成功,账单总金额 - {},账单编号 - {}",totalAmount,costInfo.getBillNumber());
            return;
        }
        throw new BasicException(SystemTip.PRICE_METER_EMPTY);
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
                CostDetailBO costDetailBO = costInfoDao.findLastCost(costInfo.getRentalId());
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
                        costDetail.setCostStartData(getCostDetailByType(price.getType(),costDetailBO.getCostDetailList()).getCostEndData());
                        //说明入住后，还没有执行过抄表策略
                        if (ObjectUtil.isEmpty(costDetail.getCostStartData())){
                            costDetail.setCostStartData(rentalInitService.findInitVal(price.getType(),rentalInfo.getId()));
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
     * 获取上次账单固定收费类型的结束日期
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