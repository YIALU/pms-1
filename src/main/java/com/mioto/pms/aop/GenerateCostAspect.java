package com.mioto.pms.aop;

import cn.hutool.core.util.StrUtil;
import com.mioto.pms.module.cost.service.ICostInfoService;
import com.mioto.pms.module.rental.model.RentalDetailDTO;
import com.mioto.pms.result.ResultData;
import com.mioto.pms.result.SystemTip;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 租住办理成功，添加账单信息
 * @author admin
 * @date 2021-07-21 16:06
 */
@Async
@Aspect
@Component
public class GenerateCostAspect {
    @Resource
    private ICostInfoService costInfoService;

    @Pointcut("execution(* com.mioto.pms.module.*.controller.RentalInfoController.add(..))")
    public void generateCost(){}

    @AfterReturning(value = "generateCost()",returning="resultData")
    public void doAfter(JoinPoint point, ResultData resultData){
        //接口返回成功，添加账单信息
        if (StrUtil.equals(resultData.getCode(), SystemTip.OK.code())) {
            Object[] args = point.getArgs();
            String roomId = ((RentalDetailDTO)args[0]).getRoomId();
            costInfoService.insertDetail(roomId);
        }
    }
}
