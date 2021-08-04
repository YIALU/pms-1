package com.mioto.pms.module.room.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author lizhicai
 * @description
 * @date 2021/4/28
 */
@Data
@ApiModel(value = "新增/编辑参数")
public class RoomDetailDTO extends  Room {
    @ApiModelProperty(value = "房屋其他资产id列表")
    private Integer[] furnitureIds;

    @ApiModelProperty(value = "设备")
    private List<RoomDeviceRelation> deviceRelations;

    @ApiModelProperty(value = "收费策略id列表")
    private Integer[] pricingStrategyIds;

}
