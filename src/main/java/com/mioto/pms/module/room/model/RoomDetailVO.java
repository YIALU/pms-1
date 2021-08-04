package com.mioto.pms.module.room.model;

import com.mioto.pms.module.device.model.Device;
import com.mioto.pms.module.furniture.model.Furniture;
import com.mioto.pms.module.price.model.Price;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author admin
 * @date 2021-07-05 14:58
 */
@Getter
@Setter
@ApiModel(value = "开户详情")
public class RoomDetailVO extends Room{

    @ApiModelProperty(value = "房屋资产列表")
    private List<Device> deviceList;

    @ApiModelProperty(value = "房屋其他资产列表")
    private List<Furniture> furnitureList;

    @ApiModelProperty(value = "费用策略列表")
    private List<Price> priceList;
}
