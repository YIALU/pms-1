package com.mioto.pms.module.site.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author lizhicai
 * @description
 * @date 2021/4/7
 */
@Data
public class SiteDTO extends Site {

    @ApiModelProperty(value = "省名称")
    private String provinceName;

    @ApiModelProperty(value = "市名称")
    private String cityName;

    @ApiModelProperty(value = "区名称")
    private String districtName;
}
