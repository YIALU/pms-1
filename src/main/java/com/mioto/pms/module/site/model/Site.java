package com.mioto.pms.module.site.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
* @auther lzc
* date 2021-04-07 17:56:51
*/
@Data
@ApiModel(value = "区域信息")
public class Site implements Serializable {
    private static final long serialVersionUID= Site.class.getName().hashCode();

    /**
    * id
    */
    @ApiModelProperty(value = "id")
    private Integer id;
    /**
    * 编号
    */
    @ApiModelProperty(value = "编号")
    private String number;

    /**
    * 区域id
    */
    @ApiModelProperty(value = "区域id")
    private String districtId;

    /**
    * 详细地址
    */
    @ApiModelProperty(value = "详细地址")
    private String address;

    @ApiModelProperty(value = "房东id")
    private Integer userId;
}
