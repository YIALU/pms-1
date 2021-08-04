package com.mioto.pms.quartz;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.text.StrFormatter;
import cn.hutool.core.util.StrUtil;
import cn.hutool.cron.CronUtil;
import cn.hutool.cron.task.Task;

import java.util.Date;
import java.util.Objects;

/**
 * @author qinxj
 * @date 2020/11/26 13:54
 */
public class QuartzManager {
    protected final String month = "month";
    protected final String day = "day";
    protected final String hour = "hour";
    protected final String minute = "minute";
    protected final String second = "second";

    /**
     * 创建间隔时间执行Quartz表达式
     * @param interval 间隔时间
     * @param intervalUnit 间隔时间单位 month day hour minute second
     * @return Quartz表达式
     */
    public String createIntervalQuartz(int interval,String intervalUnit){
        String schedulingPattern = StrUtil.EMPTY;
        Date date = new Date();
        // 从当前时间开始计算
        //每几个月执行一次
        if (Objects.equals(intervalUnit,month)){
            schedulingPattern = StrFormatter.format("{} {} {} {} {}/{} ? *", DateUtil.second(date),DateUtil.minute(date),DateUtil.hour(date,true),DateUtil.dayOfMonth(date),interval);
        }//每几天执行一次
        else if (Objects.equals(intervalUnit,day)){
            schedulingPattern = StrFormatter.format("{} {} {} */{} * ? *",DateUtil.second(date),DateUtil.minute(date),DateUtil.hour(date,true),interval);
        }//每几个小时执行一次
        else if (Objects.equals(intervalUnit,hour)){
            schedulingPattern = StrFormatter.format("{} {} */{} * * ? *",DateUtil.second(date),DateUtil.minute(date),interval);
        }//每几分钟执行一次
        else if (Objects.equals(intervalUnit,minute)){
            schedulingPattern = StrFormatter.format("*/{} * * * *",interval);
        }//每几秒执行一次
        else if (Objects.equals(intervalUnit,second)){
            schedulingPattern = StrFormatter.format("*/{} * * * * ? *",interval);
        }

        return schedulingPattern;
    }

    /**
     * 创建固定时间执行Quartz表达式
     * @param dateStr
     * @return Quartz表达式
     */
    public String createFixQuartz(String dateStr){
        String schedulingPattern = "";
        Date date;
        if (StrUtil.isNotEmpty(dateStr)){
            //如果包含年月日时分秒 说明只执行一次
            if (dateStr.length() == "yyyy-MM-dd HH:mm:ss".length()){
                date = DateUtil.parse(dateStr,"yyyy-MM-dd HH:mm:ss");
                schedulingPattern = StrFormatter.format("{} {} {} {} {} ? {}",DateUtil.second(date),DateUtil.minute(date),DateUtil.hour(date,true),
                        DateUtil.dayOfMonth(date),DateUtil.month(date) + 1,DateUtil.year(date));
            }//每个月的多少时间执行
            else if (dateStr.length() == "MM-dd HH:mm:ss".length()){
                date = DateUtil.parse(dateStr,"MM-dd HH:mm:ss");
                schedulingPattern = StrFormatter.format("{} {} {} {} {} ?",DateUtil.second(date),DateUtil.minute(date),DateUtil.hour(date,true),
                        DateUtil.dayOfMonth(date),DateUtil.month(date) + 1);
            }//每天的多少时间执行
            else if (dateStr.length() == "dd HH:mm:ss".length()){
                date = DateUtil.parse(dateStr,"dd HH:mm:ss");
                schedulingPattern = StrFormatter.format("{} {} {} {} * ?",DateUtil.second(date),DateUtil.minute(date),DateUtil.hour(date,true),
                        DateUtil.dayOfMonth(date));
            }//每小时的多少时间执行
            else if (dateStr.length() == "HH:mm:ss".length()){
                date = DateUtil.parse(dateStr,"HH:mm:ss");
                schedulingPattern = StrFormatter.format("{} {} {} * * ?",DateUtil.second(date),DateUtil.minute(date),DateUtil.hour(date,true));
            }//每分钟多少秒执行
            else if (dateStr.length() == "mm:ss".length()){
                date = DateUtil.parse(dateStr,"mm:ss");
                schedulingPattern = StrFormatter.format("{} {} * * * ?", DateUtil.second(date),DateUtil.minute(date));
            }//每秒执行
            else if (dateStr.length() == "ss".length()){
                date = DateUtil.parse(dateStr,"ss");
                schedulingPattern = StrFormatter.format("{} * * * * ?",DateUtil.second(date));
            }
        }
        return schedulingPattern;
    }

    /**
     * 添加动态任务
     * @param schedulerId 动态任务id
     * @param schedulingPattern 执行表达式
     * @param task 任务主体
     */
    public void startTask(String schedulerId, String schedulingPattern, Task task){
        CronUtil.schedule(schedulerId,schedulingPattern, task);
        CronUtil.setMatchSecond(true);
        if (!CronUtil.getScheduler().isStarted()) {
            CronUtil.start();
        }
    }

    /**
     * 根据任务id删除动态任务
     * @param schedulerId
     */
    public void deleteDynamicTask(String schedulerId){
        CronUtil.remove(schedulerId);
    }
}
