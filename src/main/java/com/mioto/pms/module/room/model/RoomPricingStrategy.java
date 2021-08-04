package com.mioto.pms.module.room.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author admin
 * @date 2021-07-05 10:25
 */
@Getter
@Setter
@ApiModel(value = "房屋收费策略关系")
public class RoomPricingStrategy {
    @ApiModelProperty(value = "主键id")
    private Integer id;
    @ApiModelProperty(value = "房屋id")
    private String roomId;
    @ApiModelProperty(value = "收费策略id")
    private Integer pricingStrategyId;
}
