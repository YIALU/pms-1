package com.mioto.pms.module.cost.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author admin
 * @date 2021-07-26 14:05
 */
@Getter
@Setter
@ApiModel(value = "缴费纪录列表对象")
public class PayListVO {
    @ApiModelProperty(value = "房间信息")
    private String roomInfo;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "缴费时间")
    private Date payTime;

    @ApiModelProperty(value = "缴费类型")
    private String costType;

    @ApiModelProperty(value = "缴费金额")
    private Double payAmount;

    @ApiModelProperty(value = "支付方式 1-微信 2-现金")
    private Integer payType;
}
