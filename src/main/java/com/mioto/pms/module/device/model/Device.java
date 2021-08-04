package com.mioto.pms.module.device.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author lizhicai
 * @description
 * @date 2021/3/19
 */
@Data
@ApiModel(value = "设备信息")
public class Device implements Serializable {
    private static final long serialVersionUID=Device.class.getName().hashCode();

    /**
     * 主键id
     */
    @ApiModelProperty(value = "主键id")
    private Integer id;

    @ApiModelProperty(value = "设备id")
    private String deviceId;

    @ApiModelProperty(value = "集中器id")
    private String focus;

    @ApiModelProperty(value = "设备名称")
    private String name;

    @ApiModelProperty(value = "设备类型id")
    private String deviceTypeId;

    @ApiModelProperty(value = "设备型号")
    private String deviceModel;

    @ApiModelProperty(value = "厂商")
    private String factory;

    @ApiModelProperty(value = "二维码文件id")
    private String code;

    @ApiModelProperty(value = "备注")
    private String desc;

    @ApiModelProperty(value = "所属房间")
    private String roomId;

    @ApiModelProperty(value = "保价")
    private Double price;

    @ApiModelProperty(value = "最近抄表数据")
    private String lastData;

    @ApiModelProperty (value = "最近上线时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date lastTime;


}
