package com.mioto.pms.module.meter.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
* @auther lzc
* date 2021-04-16 16:13:24
*/
@Data
@ApiModel(value = "抄表策略")
public class MeterReading implements Serializable {
    private static final long serialVersionUID= MeterReading.class.getName().hashCode();

    /**
    * id
    */
    @ApiModelProperty(value = "id")
    private String id;
    /**
    * 序号
    */
    @ApiModelProperty(value = "序号")
    private String number;

    /**
    * 策略名称
    */
    @ApiModelProperty(value = "策略名称")
    private String name;

    /**
    * 策略类型
    */
    @ApiModelProperty(value = "策略类型")
    private String type;


    @ApiModelProperty(value = "抄表日期")
    private String date;

    @ApiModelProperty(value = "抄表时间")
    private String time;


    @ApiModelProperty(value = "所属房东id")
    private Integer userId;

    @ApiModelProperty(value = "抄表频率 1-每月 2-每年   -- 遗弃字段")
    private Integer frequency;
}
