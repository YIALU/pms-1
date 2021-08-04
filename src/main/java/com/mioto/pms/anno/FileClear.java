package com.mioto.pms.anno;
import java.lang.annotation.*;

/**
 * @author lizhicai
 * @description
 * @date 2021/4/7
 */
@Target(ElementType.METHOD)//注解放置的目标位置,METHOD是可注解在方法级别上
@Retention(RetentionPolicy.RUNTIME)//注解在哪个阶段执行
@Documented//生成文档
public @interface FileClear {
}
