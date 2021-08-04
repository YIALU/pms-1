package com.mioto.pms.module.meter;

/**
 * 抄表策略修改类型
 * @author admin
 * @date 2021-07-26 18:48
 */
public enum MeterReadType {
    /**
     * 租住办理成功，新增抄表策略任务
     */
    INSERT,
    /**
     * 1.编辑租住办理，修改房间信息
     * 2.编辑房间对应的抄表策略
     */
    UPDATE,
    /**
     * 退租，删除任务
     */
    DELETE;
}
