package com.mioto.pms.module.cost.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author admin
 * @date 2021-08-28 15:03
 */
@Data
@ApiModel(value = "附加收费类型")
public class CostType {
    private String id;

    @ApiModelProperty(value = "收费名称")
    private String name;
}
