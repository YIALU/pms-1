package com.mioto.pms.pool;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author mioto-qinxj
 * @description  原生(Spring)异步任务线程池装配类
 * @date 2020/4/6
 */
@Slf4j
@Configuration
public class NativeAsyncTaskExecutePool implements AsyncConfigurer {
    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //核心线程池大小
        executor.setCorePoolSize(5);
        //最大线程数
        executor.setMaxPoolSize(40);
        //队列容量
        executor.setQueueCapacity(300);
        //活跃时间
        executor.setKeepAliveSeconds(50);
        //线程名字前缀
        executor.setThreadNamePrefix("sync-executor-");
        // setRejectedExecutionHandler：当pool已经达到max size的时候，如何处理新任务
        // CallerRunsPolicy：不在新线程中执行任务，而是由调用者所在的线程来执行
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 等待所有任务结束后再关闭线程池
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.initialize();
        return executor;
    }

    /**
     * 异步任务中异常处理
     * @return
     */
    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return (arg0,arg1,arg2)->{
            log.error("=========================="+arg0.getMessage()+"=======================", arg0);
            log.error("exception method:{}",arg1.getName());
        };
    }
}
