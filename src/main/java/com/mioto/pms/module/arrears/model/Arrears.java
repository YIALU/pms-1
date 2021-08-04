package com.mioto.pms.module.arrears.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
* @auther lzc
* date 2021-04-28 15:31:26
*/
@Data
@ApiModel(value = "欠费策略")
public class Arrears implements Serializable {
    private static final long serialVersionUID= Arrears.class.getName().hashCode();

    @ApiModelProperty(value = "id")
    private Integer id;

    @ApiModelProperty(value = "策略名称")
    private String name;

    @ApiModelProperty(value = "执行时间")
    private Integer date;

    @ApiModelProperty(value = "执行操作")
    private String operation;

    @ApiModelProperty(value = "备注")
    private String desc;

    @ApiModelProperty(value = "创建用户id")
    private Integer userId;
}
