package com.mioto.pms.module.furniture.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author admin
 * @date 2021-07-06 9:41
 */
@ApiModel(value = "查询资产列表返回对象")
@Getter
@Setter
public class FurnitureListVO {
    @ApiModelProperty(value = "id")
    private Integer id;
    @ApiModelProperty(value = "家具名字")
    private String name;
    @ApiModelProperty(value = "价格")
    private Double price;
    @ApiModelProperty(value = "序号")
    private String number;
    @ApiModelProperty(value = "图片id列表，多张图片使用,分隔")
    private String fileId;
    @ApiModelProperty(value = "区域地址")
    private String siteAddress;
}
