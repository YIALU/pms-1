package com.mioto.pms.anno;

import com.mioto.pms.module.meter.MeterReadType;

import java.lang.annotation.*;

/**
 * 抄表任务注解
 * @author admin
 * @date 2021-07-16 15:20
 */
@Target(ElementType.METHOD)//注解放置的目标位置,METHOD是可注解在方法级别上
@Retention(RetentionPolicy.RUNTIME)//注解在哪个阶段执行
@Documented//生成文档
public @interface MeterReadingAnno {
    MeterReadType type() default MeterReadType.INSERT;
}
