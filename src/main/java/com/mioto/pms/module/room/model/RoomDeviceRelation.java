package com.mioto.pms.module.room.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author admin
 * @date 2021-07-01 9:46
 */
@Getter
@Setter
@ApiModel(value = "房屋与设备关系 - 用于添加关系")
public class RoomDeviceRelation {
    @ApiModelProperty(value = "设备主键id")
    private Integer deviceId;
    @ApiModelProperty(value = "保价")
    private Double price;
}
