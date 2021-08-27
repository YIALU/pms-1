package com.mioto.pms.module.weixin.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author admin
 * @date 2021-08-11 12:00
 */
@Getter
@Setter
@ApiModel(value = "合同收费策略信息")
public class ContractPriceVO {
    @ApiModelProperty(value = "策略名")
    private String name;

    @ApiModelProperty(value = "金额")
    private String amount;
}
