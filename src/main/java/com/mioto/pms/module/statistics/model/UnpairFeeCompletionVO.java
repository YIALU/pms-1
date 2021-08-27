package com.mioto.pms.module.statistics.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author admin
 * @date 2021-08-19 17:00
 */
@Getter
@Setter
@ToString
@ApiModel(value = "统计页面 - 欠费列表对象")
public class UnpairFeeCompletionVO {
    @ApiModelProperty(value = "欠费金额")
    private Double amount;
    @ApiModelProperty(value = "租户名")
    private String tenantName;
    @ApiModelProperty(value = "地址")
    private String address;
    @ApiModelProperty(value = "欠费天数")
    private int unpairFeeDay;
}
