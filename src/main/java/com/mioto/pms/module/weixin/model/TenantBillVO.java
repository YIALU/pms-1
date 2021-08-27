package com.mioto.pms.module.weixin.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author admin
 * @date 2021-08-10 14:36
 */
@Getter
@Setter
@ApiModel(value = "租户账单列表")
public class TenantBillVO {
    @ApiModelProperty(value = "主账单id")
    private String costInfoId;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @ApiModelProperty(value = "账单创建时间")
    private Date createTime;
    @ApiModelProperty(value = "待支付金额")
    private Double amount;
}
