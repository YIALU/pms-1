package com.mioto.pms.component.bill;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.mioto.pms.module.cost.dao.CostInfoDao;
import com.mioto.pms.module.cost.model.CostDetail;
import com.mioto.pms.module.cost.model.CostDetailBO;
import com.mioto.pms.module.cost.model.CostInfo;
import com.mioto.pms.module.cost.service.ICostDetailService;
import com.mioto.pms.module.device.DeviceTypeEnum;
import com.mioto.pms.module.device.model.Device;
import com.mioto.pms.module.device.service.IDeviceService;
import com.mioto.pms.module.meter.model.MeterReading;
import com.mioto.pms.module.meter.service.MeterReadingService;
import com.mioto.pms.module.price.PricingStrategyEnum;
import com.mioto.pms.module.price.model.Price;
import com.mioto.pms.module.price.service.PriceService;
import com.mioto.pms.module.rental.model.RentalInfo;
import com.mioto.pms.module.rental.service.IRentalInfoService;
import com.mioto.pms.module.rental.service.IRentalInitService;
import com.mioto.pms.utils.BaseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * 自动抄表
 * @author admin
 * @date 2021-09-03 16:06
 */
@Component
@Slf4j
public class AutoMeterReadBill extends AbstractBill {
    @Resource
    private IRentalInfoService rentalInfoService;
    @Resource
    private PriceService priceService;
    @Resource
    private CostInfoDao costInfoDao;
    @Resource
    private ICostDetailService costDetailService;
    @Resource
    private MeterReadingService meterReadingService;
    @Resource
    private IRentalInitService rentalInitService;
    @Resource
    private IDeviceService deviceService;

    private CostInfo costInfo;

    public AutoMeterReadBill() {
        super.genMainBill = true;
    }

    @Override
    protected double createChildBill(Object obj) {
        synchronized (this){
            String roomId = (String) obj;
            List<RentalInfo> rentalInfoList = rentalInfoService.findByRoomIdAndStatus(roomId,1);
            double totalAmount = 0d;
            if (CollUtil.isNotEmpty(rentalInfoList) && rentalInfoList.size() == 1) {
                RentalInfo rentalInfo = rentalInfoList.get(0);
                costInfo = builderCostInfo(rentalInfo.getId());
                List<Price> priceList = priceService.findByRoomId(rentalInfo.getRoomId());
                if (CollUtil.isNotEmpty(priceList)){
                    //查询抄表时间
                    MeterReading meterReading = meterReadingService.findByRoomId(rentalInfo.getRoomId());
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
                                List<Device> deviceList = deviceService.findByRoomId(roomId);
                                for (Device device : deviceList) {
                                    if (DeviceTypeEnum.getInstance(device.getDeviceTypeId()).getPricingStrategyEnum() == pricingStrategyEnum) {
                                        costDetail.setCostEndData(meterReadingService.findDataByDeviceIdAndType(device.getId(),device.getDeviceTypeId()));
                                        Double intervalData = NumberUtil.sub(costDetail.getCostEndData(), costDetail.getCostStartData());
                                        costDetail.setAmount(NumberUtil.round(NumberUtil.mul(costDetail.getUnit(), intervalData), 2).doubleValue());
                                        break;
                                    }
                                }
                            }
                            totalAmount = NumberUtil.add(totalAmount, costDetail.getAmount().doubleValue());
                            index++;
                            costDetailService.insertIgnoreNull(costDetail);
                            log.info("子账单生成成功,账单类型 - {},账单金额 - {},账单编号 - {}",pricingStrategyEnum.name(),costDetail.getAmount(),costDetail.getBillChildNumber());
                        }
                    }
                }
            }
            return totalAmount;
        }
    }

    @Override
    protected void createMainBill(double totalAmount) {
        costInfo.setTotalAmount(totalAmount);
        costInfoDao.insertIgnoreNull(costInfo);
        log.info("主账单生成成功,账单编号 - {}",costInfo.getBillNumber());
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

    private CostDetail builderCostDetail(CostInfo costInfo,Price price,int index){
        CostDetail costDetail = new CostDetail();
        costDetail.setCostInfoId(costInfo.getId());
        costDetail.setBillChildNumber(costInfo.getBillNumber() + StrUtil.DASHED + index);
        costDetail.setUnit(price.getUnitPrice());
        costDetail.setCostType(price.getType());
        costDetail.setType(ICostDetailService.BILL_TYPE_GEN);
        return costDetail;
    }

    private CostInfo builderCostInfo(String rentalId){
        CostInfo costInfo = new CostInfo();
        costInfo.setRentalId(rentalId);
        costInfo.setBillNumber(BaseUtil.createBillNumber());
        costInfo.setId(IdUtil.simpleUUID());
        return costInfo;
    }
}
