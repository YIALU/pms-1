package com.mioto.pms.module.statistics.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author admin
 * @date 2021-08-20 15:00
 */
@Getter
@Setter
@ToString
@ApiModel(value = "收费类型对象")
public class CostTypeVO {

    @ApiModelProperty(value = "收费金额")
    private Double amount;

    @ApiModelProperty(value = "收费类型")
    private String costType;

    public String getCostType() {
        return costType;
    }
}
