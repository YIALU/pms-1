package com.mioto.pms.module.cost.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author admin
 * @date 2021-07-22 16:12
 */
@Getter
@Setter
@ApiModel(value = "子账单列表对象")
public class CostDetailListVO {
    @ApiModelProperty(value = "子账单id")
    private Integer id;
    @ApiModelProperty(value = "子账单编号")
    private String childBillNumber;
    @ApiModelProperty(value = "费用类型")
    private String costType;
    @ApiModelProperty(value = "费用类型id")
    private String costTypeId;
    @ApiModelProperty(value = "账单起始数据")
    private Double startData;
    @ApiModelProperty(value = "账单结束数据")
    private Double endData;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @ApiModelProperty(value = "开始日期")
    private Date startDate;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @ApiModelProperty(value = "结束日期")
    private Date endDate;
    @ApiModelProperty(value = "总金额")
    private Double amount;
    @ApiModelProperty(value = "单价")
    private Double unit;
    @ApiModelProperty(value = "支付状态 0-未支付  1-已支付")
    private int payStatus;
    @ApiModelProperty(value = "账单类型 1-系统生成  2-手动添加")
    private Integer type;
}
