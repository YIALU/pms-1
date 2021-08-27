package com.mioto.pms.module.site.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author lizhicai
 * @description
 * @date 2021/4/8
 */
@Data
public class SiteExcel {

    @ApiModelProperty(value = "省名称")
    private String provinceName;

    @ApiModelProperty(value = "市名称")
    private String cityName;

    @ApiModelProperty(value = "区名称")
    private String districtName;


    @ApiModelProperty(value = "详细地址")
    private String address;

    @ApiModelProperty(value = "用户id")
    private Integer userId;
}
