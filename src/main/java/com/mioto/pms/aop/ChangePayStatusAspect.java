package com.mioto.pms.aop;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.EnumUtil;
import cn.hutool.core.util.StrUtil;
import com.mioto.pms.anno.PayStatusChange;
import com.mioto.pms.module.cost.PayTypeEnum;
import com.mioto.pms.module.cost.model.CostInfo;
import com.mioto.pms.module.cost.service.ICostInfoService;
import com.mioto.pms.result.ResultData;
import com.mioto.pms.result.SystemTip;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 支付成功，修改支付状态
 * @author admin
 * @date 2021-07-26 17:15
 */
@Aspect
@Component
public class ChangePayStatusAspect {
    @Resource
    private ICostInfoService costInfoService;

    @Pointcut("@annotation(com.mioto.pms.anno.PayStatusChange)")
    public void changePayStatusAspect(){}

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
                    changePayStatus(billNumbers);
                }
            }else if (payStatusChange.type() == PayTypeEnum.MINI_CASH){
                String[] billNumbers = (String[]) args[0];
                billNumbers = Arrays.stream(billNumbers)
                        .map(billNumber -> billNumber.substring(0,billNumber.indexOf(StrUtil.DASHED)))
                        .collect(Collectors.toSet()).stream().toArray(String[]::new);
                changePayStatus(billNumbers);
            }
        }
    }

    private void changePayStatus(String[] billNumbers){
        List<CostInfo> costInfoList = costInfoService.findByBillNumbers(billNumbers);
        billNumbers = costInfoList.stream().filter(costInfo -> costInfo.getPayStatus() == 1)
                .map(costInfo -> costInfo.getBillNumber()).toArray(String[]::new);
        if (ArrayUtil.isNotEmpty(billNumbers)) {
            costInfoService.batchChangePayStatus(billNumbers);
        }
    }
}
