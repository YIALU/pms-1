package com.mioto.pms.module.cost.model;

import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
/**
 * 支付信息实体
 * @author qinxj
 * @date 2021-07-23 15:12:53
 */
@Data
@ApiModel(value = "支付信息")
public class PayInfo implements Serializable{

    private static final long serialVersionUID=PayInfo.class.getName().hashCode();
    

    @ApiModelProperty(value = "id")
    private Integer id;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "支付时间")
    private Date payTime;

    @ApiModelProperty(value = "支付类型 1-微信 2-现金")
    private Integer payType;

    @ApiModelProperty(value = "支付金额")
    private Double payAmount;

    @ApiModelProperty(value = "账单编号")
    private String billNumber;
    
}