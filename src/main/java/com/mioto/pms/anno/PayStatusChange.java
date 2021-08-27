package com.mioto.pms.anno;

import com.mioto.pms.module.cost.PayTypeEnum;

import java.lang.annotation.*;

/**
 * 支付状态修改注解
 * @author admin
 * @date 2021-07-26 17:14
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PayStatusChange {
    PayTypeEnum type() default PayTypeEnum.WEB_CASH;
}
