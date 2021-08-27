package com.mioto.pms.module.weixin.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author admin
 * @date 2021-08-10 16:28
 */
@Getter
@Setter
@ApiModel(value = "房屋未缴费账单明细")
public class UnpaidFeesVO {
    @ApiModelProperty(value = "子账单编号")
    private String billChildNumber;
    @ApiModelProperty(value = "费用名")
    private String costName;
    @ApiModelProperty(value = "当月未缴费总金额")
    private Double amount;
    @ApiModelProperty(value = "起始数据")
    private Double startData;
    @ApiModelProperty(value = "结束数据")
    private Double endData;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @ApiModelProperty(value = "账单创建时间")
    private Date createTime;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @ApiModelProperty(value = "起始时间")
    private Date startTime;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @ApiModelProperty(value = "结束时间")
    private Date endTime;
    @ApiModelProperty(value = "费用类型id")
    private String costTypeId;
}
