package com.mioto.pms.module.personal.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
* @auther lzc
* date 2021-04-08 18:00:58
*/
@Data
@ApiModel(value = "人员管理")
public class Personal implements Serializable {
    private static final long serialVersionUID= Personal.class.getName().hashCode();

    /**
    * id
    */
    @ApiModelProperty(value = "id")
    private String id;
    /**
    * 姓名
    */
    @ApiModelProperty(value = "姓名")
    private String name;

    /**
    * 人员类型
    */
    @ApiModelProperty(value = "人员类型")
    private String personalType;

    /**
    * 电话
    */
    @ApiModelProperty(value = "电话")
    private String phone;

    /**
    * 身份证号
    */
    @ApiModelProperty(value = "身份证号")
    private String card;

    /**
    * 备注
    */
    @ApiModelProperty(value = "备注")
    private String desc;

    @ApiModelProperty(value = "房东id")
    private String userId;
}
