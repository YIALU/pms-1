package com.mioto.pms.module.cost.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
/**
 * 费用明细实体
 * @author qinxj
 * @date 2021-07-16 15:05:10
 */
@Data
@ApiModel(value = "费用明细")
public class CostDetail implements Serializable{

    private static final long serialVersionUID=CostDetail.class.getName().hashCode();
    

    @ApiModelProperty(value = "id")
    private Integer id;

    @ApiModelProperty(value = "费用信息id")
    private String costInfoId;

    @ApiModelProperty(value = "账单子编号")
    private String billChildNumber;

    @ApiModelProperty(value = "费用类型 ")
    private String costType;

    @ApiModelProperty(value = "账单开始数据")
    private Double costStartData;

    @ApiModelProperty(value = "账单结束数据")
    private Double costEndData;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "账单开始时间")
    private Date costStartTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "账单结束时间")
    private Date costEndTime;

    @ApiModelProperty(value = "单价")
    private Double unit;

    @ApiModelProperty(value = "总价")
    private Double amount;

    @ApiModelProperty(value = "账单类型 1-系统生成  2-手动添加")
    private Integer type;

    @ApiModelProperty(value = "支付状态 0-未支付 1-已支付")
    private Integer payStatus;

}