package com.mioto.pms.aop;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.mioto.pms.anno.PayStatusChange;
import com.mioto.pms.module.cost.PayTypeEnum;
import com.mioto.pms.module.cost.model.CostInfo;
import com.mioto.pms.module.cost.service.ICostDetailService;
import com.mioto.pms.module.cost.service.ICostInfoService;
import com.mioto.pms.module.rental.RentalStatus;
import com.mioto.pms.module.rental.service.IRentalInfoService;
import com.mioto.pms.result.ResultData;
import com.mioto.pms.result.SystemTip;
import com.mioto.pms.utils.BaseUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 支付成功，修改支付状态
 * @author admin
 * @date 2021-07-26 17:15
 */
@Async
@Aspect
@Component
public class ChangePayStatusAspect {
    @Resource
    private ICostInfoService costInfoService;
    @Resource
    private ICostDetailService costDetailService;
    @Resource
    private IRentalInfoService rentalInfoService;


    @Pointcut("@annotation(com.mioto.pms.anno.PayStatusChange)")
    public void changePayStatusAspect(){}

    @Transactional(rollbackFor = Exception.class)
    @AfterReturning(value = "changePayStatusAspect()",returning="resultData")
    public void doAfter(JoinPoint point, ResultData resultData){
        if (StrUtil.equals(resultData.getCode(), SystemTip.OK.code())){
            MethodSignature signature = (MethodSignature) point.getSignature();
            PayStatusChange payStatusChange = signature.getMethod().getAnnotation(PayStatusChange.class);
            Object[] args = point.getArgs();
            if (payStatusChange.type() == PayTypeEnum.WEB_CASH){
                String costType = (String) args[0];
                String[] billNumbers = (String[]) args[1];
                //全部缴费
                if (StrUtil.isEmpty(costType)){
                    costInfoService.batchChangePayStatus(billNumbers);
                }else {
                    billNumbers = changePayStatus(billNumbers);
                }
                costDetailService.batchChangePayStatus(billNumbers,costType);
                changeRentalStatus(billNumbers);
                return;
            }
            String[] billNumbers = null;
            if (payStatusChange.type() == PayTypeEnum.MINI_CASH){
                billNumbers = (String[]) args[0];
            }else if (payStatusChange.type() == PayTypeEnum.MINI){
                billNumbers = BaseUtil.getWxPaymentBillNumbers((String) args[0]);
            }
            costDetailService.editPayStatus(billNumbers);
            billNumbers = Arrays.stream(billNumbers)
                    .map(billNumber -> billNumber.substring(0,billNumber.indexOf(StrUtil.DASHED)))
                    .collect(Collectors.toSet()).stream().toArray(String[]::new);
            billNumbers = changePayStatus(billNumbers);
            changeRentalStatus(billNumbers);
        }
    }

    /**
     * 修改主账单支付状态
     * @param billNumbers
     */
    private String[] changePayStatus(String[] billNumbers){
        List<CostInfo> costInfoList = costInfoService.findByBillNumbers(billNumbers);
        billNumbers = costInfoList.stream().filter(costInfo -> costInfo.getPayStatus() == 1)
                .map(costInfo -> costInfo.getBillNumber()).toArray(String[]::new);
        if (ArrayUtil.isNotEmpty(billNumbers)) {
            costInfoService.batchChangePayStatus(billNumbers);
        }
        return billNumbers;
    }

    /**
     * 查询账单对应的租住id，如果该id下所有的款已付清，并且租住状态未正在退租中，修改改租住状态为历史租住
     * @param billNumbers 已付款主账单编号
     */
    private void changeRentalStatus(String[] billNumbers){
        if (ArrayUtil.isNotEmpty(billNumbers)) {
            String[] rentalIds = rentalInfoService.findCancelIdsByBillNumbers(billNumbers);
            if (ArrayUtil.isNotEmpty(rentalIds)){
                rentalIds = Arrays.stream(rentalIds)
                        .filter(StrUtil::isNotEmpty)
                        .toArray(String[]::new);
                if (ArrayUtil.isNotEmpty(rentalIds)){
                    rentalInfoService.updateStatusBatch(rentalIds, RentalStatus.STATUS_HISTORY);
                }
            }
        }
    }

}
