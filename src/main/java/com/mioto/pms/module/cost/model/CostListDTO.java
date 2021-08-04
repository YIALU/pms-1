package com.mioto.pms.module.cost.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author admin
 * @date 2021-07-26 15:44
 */
@Getter
@Setter
@ApiModel(value = "账单列表查询条件")
public class CostListDTO {

    @ApiModelProperty(value = "区域id")
    private String siteId;

    @ApiModelProperty(value = "租户姓名/手机号码")
    private String tenantInfo;

    @ApiModelProperty(value = "账单时间")
    private String payTime;

    @ApiModelProperty(value = "状态 0-未缴清 1-已缴清")
    private Integer payStatus;

    @ApiModelProperty(value = "单号")
    private String billNumber;

    @ApiModelProperty(value = "用户id")
    private Integer userId;
}
