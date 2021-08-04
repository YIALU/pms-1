package com.mioto.pms.module.statistics.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author admin
 * @date 2021-07-29 18:29
 */
@Getter
@Setter
@ApiModel("缴费进度统计对象")
public class PaymentProgressVO {

    @ApiModelProperty("收费完成度")
    private double feeCompletion;
}
