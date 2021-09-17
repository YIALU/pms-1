package com.mioto.pms.module.rental.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 退租数据
 * @author admin
 * @date 2021-08-21 15:00
 */
@Getter
@Setter
@ApiModel(value = "退租数据")
public class CancellationDataDTO {

    @ApiModelProperty(value = "收费类型id")
    private String costTypeId;
    @ApiModelProperty(value = "当前表读数 如果是固定类型,忽略该字段")
    private Double endData;
    @ApiModelProperty(value = "上次表读数 如果是固定类型,忽略该字段")
    private Double startData;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "上次抄表日期 如果是固定类型,忽略该字段")
    private Date startDate;
    @ApiModelProperty(value = "附加费金额")
    private Double amount;
    @ApiModelProperty(value = "附加费名称")
    private String name;
}
