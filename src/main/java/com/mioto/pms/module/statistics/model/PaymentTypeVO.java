package com.mioto.pms.module.statistics.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author admin
 * @date 2021-08-20 14:55
 */
@Getter
@Setter
@ToString
@ApiModel(value = "支付类型对象")
public class PaymentTypeVO {
    @ApiModelProperty(value = "收款金额")
    private Double payAmount;

    @ApiModelProperty(value = "支付类型 1-微信 2-现金")
    private Integer payType;

}
