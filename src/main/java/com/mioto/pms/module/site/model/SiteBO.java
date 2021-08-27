package com.mioto.pms.module.site.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author admin
 * @date 2021-08-09 18:53
 */
@Getter
@Setter
public class SiteBO {

    @ApiModelProperty(value = "省市区")
    private String area;

    @ApiModelProperty(value = "区域")
    private String siteName;
}
