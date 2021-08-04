package com.mioto.pms.module.dictionary.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
* @auther lzc
* date 2021-04-06 15:25:37
*/
@Data
@ApiModel(value = "数据字典")
public class Dictionary implements Serializable {
    private static final long serialVersionUID= Dictionary.class.getName().hashCode();

    /**
    * id
    */
    @ApiModelProperty(value = "id")
    private String id;
    /**
    * 名称
    */
    @ApiModelProperty(value = "名称")
    private String name;

    /**
    * 数据类型
    */
    @ApiModelProperty(value = "数据类型")
    private String type;

    /**
    * 数据编码
    */
    @ApiModelProperty(value = "数据编码")
    private String code;

    /**
    * 数据层级
    */
    @ApiModelProperty(value = "数据层级")
    private String level;

    /**
    * 备注
    */
    @ApiModelProperty(value = "备注")
    private String desc;

    /**
    * 状态 0-禁用 1-启用
    */
    @ApiModelProperty(value = "状态 0-禁用 1-启用")
    private Integer status;

    /**
    * 父id
    */
    @ApiModelProperty(value = "父id")
    private String pid;


}
