package com.mioto.pms.module.statistics.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author admin
 * @date 2021-09-04 14:05
 */
@Getter
@Setter
@ApiModel(value = "用能统计对象")
public class EnergyVO {
    @ApiModelProperty(value = "按周统计时 0-6 周日为0")
    private int day;
    @ApiModelProperty(value = "使用量")
    private double usageAmount;
}
