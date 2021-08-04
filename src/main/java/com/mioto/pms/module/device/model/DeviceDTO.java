package com.mioto.pms.module.device.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @author lizhicai
 * @description
 * @date 2021/4/7
 */
@Getter
@Setter
public class DeviceDTO extends Device {

    @ApiModelProperty (value = "在线状态 0-离线 1-在线")
    private Integer status = 0;

    @ApiModelProperty(value = "关联房间名称")
    private String roomName;
}
