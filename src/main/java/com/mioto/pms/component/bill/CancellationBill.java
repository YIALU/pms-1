package com.mioto.pms.component.bill;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import com.mioto.pms.module.cost.dao.CostInfoDao;
import com.mioto.pms.module.cost.model.CostDetail;
import com.mioto.pms.module.cost.model.CostDetailBO;
import com.mioto.pms.module.cost.model.CostInfo;
import com.mioto.pms.module.cost.service.ICostDetailService;
import com.mioto.pms.module.price.PricingStrategyEnum;
import com.mioto.pms.module.price.model.Price;
import com.mioto.pms.module.price.service.PriceService;
import com.mioto.pms.module.rental.model.CancellationDTO;
import com.mioto.pms.module.rental.model.CancellationDataDTO;
import com.mioto.pms.module.rental.model.RentalDetailDTO;
import com.mioto.pms.module.rental.model.RentalInfo;
import com.mioto.pms.module.rental.service.IRentalInfoService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 退租账单
 * @author admin
 * @date 2021-08-21 14:52
 */
@Component
public class CancellationBill extends AbstractBill{
    @Resource
    private IRentalInfoService rentalInfoService;
    @Resource
    private PriceService priceService;
    @Resource
    private CostInfoDao costInfoDao;
    @Resource
    private ICostDetailService costDetailService;


    private CostDetailBO costDetailBO;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public double createChildBill(Object obj) {
        CancellationDTO cancellationDTO = (CancellationDTO) obj;
        RentalInfo rentalInfo = rentalInfoService.findByColumn("id",cancellationDTO.getRentalId());
        List<Price> priceList = priceService.findByRoomId(rentalInfo.getRoomId());
        Map<String,Price> priceMap = priceList.stream().collect(Collectors.toMap(Price::getType,Price::get));
        costDetailBO = costInfoDao.findLastCost(cancellationDTO.getRentalId());
        int costDetailSize = costDetailBO.getCostDetailList().size();

        List<CancellationDataDTO> cancellationDataDTOList = cancellationDTO.getCancellationDataDTOList();
        CostDetail costDetail;
        Date currentDate = new Date();
        double totalAmount = 0d;
        for (CancellationDataDTO cancellationDataDTO : cancellationDataDTOList) {
            if (PricingStrategyEnum.getInstance(cancellationDataDTO.getCostTypeId()).getType() == 2){
                continue;
            }
            costDetail = new CostDetail();
            costDetail.setCostInfoId(costDetailBO.getId());
            costDetail.setBillChildNumber(costDetailBO.getBillNumber() + StrUtil.DASHED + ++costDetailSize);
            costDetail.setUnit(priceMap.get(cancellationDataDTO.getCostTypeId()).getUnitPrice());
            costDetail.setCostType(priceMap.get(cancellationDataDTO.getCostTypeId()).getType());
            costDetail.setType(ICostDetailService.BILL_TYPE_GEN);
            costDetail.setCostEndTime(currentDate);
            costDetail.setCostStartTime(cancellationDataDTO.getStartDate());
            if (PricingStrategyEnum.getInstance(cancellationDataDTO.getCostTypeId()).getType() == 1){
                costDetail.setCostEndData(cancellationDataDTO.getEndData());
                costDetail.setCostStartData(cancellationDataDTO.getStartData());
                costDetail.setCostStartTime(cancellationDataDTO.getStartDate());
                Double intervalData = NumberUtil.sub(costDetail.getCostEndData(), costDetail.getCostStartData());
                costDetail.setAmount(NumberUtil.round(NumberUtil.mul(costDetail.getUnit(), intervalData), 2).doubleValue());
            }
            else if (PricingStrategyEnum.getInstance(cancellationDataDTO.getCostTypeId()).getType() == 3){
                costDetail.setAmount(cancellationDataDTO.getAmount());
            }
            costDetailService.insertIgnoreNull(costDetail);
            totalAmount = NumberUtil.add(totalAmount, costDetail.getAmount().doubleValue());
        }

        return totalAmount;
    }

    @Override
    protected void updateMainBill(double totalAmount) {
        CostInfo costInfo = new CostInfo();
        costInfo.setTotalAmount(NumberUtil.add(costDetailBO.getTotalAmount(),(Double) totalAmount));
        costInfo.setId(costDetailBO.getId());
        costInfo.setPayStatus(0);
        costInfo.setIsSend(1);
        costInfoDao.updateIgnoreNull(costInfo);
    }

    @Override
    protected void hook(){
        RentalDetailDTO rentalInfo = new RentalDetailDTO();
        rentalInfo.setId(costDetailBO.getRentalId());
        rentalInfo.setStatus(2);
        rentalInfoService.updateIgnoreNull(rentalInfo);
    }
}
