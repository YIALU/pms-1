package com.mioto.pms.module.furniture.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
* @auther lzc
* date 2021-04-21 16:22:29
*/
@Data
@ApiModel(value = "资产管理")
public class Furniture implements Serializable {
    private static final long serialVersionUID= Furniture.class.getName().hashCode();

    @ApiModelProperty(value = "id")
    private Integer id;

    @ApiModelProperty(value = "家具名字")
    private String name;

    @ApiModelProperty(value = "所属房间id")
    private String roomId;

    @ApiModelProperty(value = "区域id")
    private Integer siteId;

    @ApiModelProperty(value = "图片id列表，多张图片使用,分隔")
    private String fileId;

    @ApiModelProperty(value = "价格")
    private Double price;

    @ApiModelProperty(value = "序号")
    private String number;
    @ApiModelProperty(value = "房东id")
    private Integer userId;

}
