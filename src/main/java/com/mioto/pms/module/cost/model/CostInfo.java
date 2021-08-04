package com.mioto.pms.module.cost.model;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
/**
 * 费用信息实体
 * @author qinxj
 * @date 2021-07-16 15:05:11
 */
@Data
@ApiModel(value = "费用信息")
public class CostInfo implements Serializable{

    private static final long serialVersionUID=CostInfo.class.getName().hashCode();
    

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "租住id")
    private String rentalId;

    @ApiModelProperty(value = "账单编号")
    private String billNumber;

    @ApiModelProperty(value = "总金额")
    private Double totalAmount;

    @ApiModelProperty(value = "支付状态 0-未缴清 1-已缴清")
    private Integer payStatus;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "费用生成时间")
    private Date createTime;

    @ApiModelProperty(value = "账单是否发送 0-否 1-是")
    private Integer isSend;
}