package com.mioto.pms.module.cost.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Calendar;

/**
 * @author admin
 * @date 2021-07-26 14:11
 */
@Getter
@Setter
@ApiModel(value = "查询缴费纪录条件对象")
public class PayListDTO {
    @ApiModelProperty(value = "缴费类型")
    private String costType;

    @ApiModelProperty(value = "支付方式 1-微信 2-现金")
    private Integer payType;

    @ApiModelProperty(value = "支付时间")
    private String payTime;

    @ApiModelProperty(value = "租户姓名/手机号码")
    private String tenantInfo;

    @ApiModelProperty(value = "区域id")
    private Integer siteId;

    @ApiModelProperty(value = "用户id")
    private Integer userId;

}
