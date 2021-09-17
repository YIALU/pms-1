package com.mioto.pms.module.meter.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * 抄表数据
 * @author admin
 * @date 2021-09-03 14:11
 */
@Getter
@Setter
@ToString
public class MeterData {
    private int id;
    private int deviceId;
    private double value;
    private Date createDate;
    private double usageAmount;
    private String deviceType;


}
