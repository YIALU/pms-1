package com.mioto.pms.module.rental.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * @author admin
 * @date 2021-08-13 17:40
 */
@Getter
@Setter
@ToString
@ApiModel(value = "租住初始数据")
public class RentalInit {
    @ApiModelProperty(value = "主键id")
    private Integer id;
    @ApiModelProperty(value = "房屋租住id")
    private String rentalId;
    @ApiModelProperty(value = "房屋收费类型")
    private String costType;
    @ApiModelProperty(value = "初始值")
    private Double initVal;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
}
