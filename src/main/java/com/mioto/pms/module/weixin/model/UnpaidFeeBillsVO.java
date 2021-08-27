package com.mioto.pms.module.weixin.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author admin
 * @date 2021-08-17 15:33
 */
@Getter
@Setter
@ApiModel(value = "未收款账单")
public class UnpaidFeeBillsVO {
    @ApiModelProperty(value = "租住id")
    private String rentalId;
    @ApiModelProperty(value = "房间地址")
    private String address;
    @ApiModelProperty(value = "主账单id")
    private String costInfoId;
    @ApiModelProperty(value = "未收金额")
    private Double amount;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @ApiModelProperty(value = "账单创建时间")
    private Date createTime;
}
