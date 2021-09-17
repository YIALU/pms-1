package com.mioto.pms.module.basic.service;

import com.mioto.pms.module.basic.model.Scheduler;

import java.util.List;

/**
 * @author admin
 * @date 2021-08-27 14:52
 */
public interface IBasicService {

    boolean sendVerCodeSms(String phone);

    boolean sendoVerdueNotifySms(String phone);


    boolean sendMeterReadingCmd(String roomId);

    boolean saveScheduler(Scheduler scheduler);

    boolean removeScheduler(String schedulerId);

    List<Scheduler> findListByType(String schedulerType);
}
