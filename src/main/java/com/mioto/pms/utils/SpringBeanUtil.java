package com.mioto.pms.utils;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author mioto-qinxj
 * @description
 * @date 2020/4/3
 */
@Component
public class SpringBeanUtil implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    /**
     * Spring容器会在创建该Bean之后，自动调用该Bean的setApplicationContext方法
     *
     * @param applicationContext
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        if (SpringBeanUtil.applicationContext == null) {
            SpringBeanUtil.applicationContext = applicationContext;
        }
    }

    //获取applicationContext
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    //通过name获取 Bean.
    public static Object getBean(String name) {
        return getApplicationContext().getBean(name);
    }

    //通过class获取Bean.
    public static <T> T getBean(Class<T> clazz) {
        return getApplicationContext().getBean(clazz);
    }

    //通过name,以及Clazz返回指定的Bean
    public static <T> T getBean(String name, Class<T> clazz) {
        return getApplicationContext().getBean(name, clazz);
    }

}
