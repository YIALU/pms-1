package com.mioto.pms.module.statistics.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @author admin
 * @date 2021-08-20 14:34
 */
@Getter
@Setter
@ToString
@ApiModel(value = "缴费统计对象")
public class PaymentVO {

    @ApiModelProperty(value = "收费类型列表")
    private List<CostTypeVO> costTypeVOList;

    @ApiModelProperty(value = "支付类型列表")
    private List<PaymentTypeVO> paymentTypeVOList;
}
