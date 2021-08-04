package com.mioto.pms.module.user.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
* @auther lzc
* date 2021-04-01 16:24:10
*/
@Data
@ApiModel(value = "权限")
public class Function implements Serializable {
    private static final long serialVersionUID= Function.class.getName().hashCode();

    /**
    * id
    */
    @ApiModelProperty(value = "id")
    private Integer id;
    /**
    * 功能名称
    */
    @ApiModelProperty(value = "功能名称")
    private String name;

    /**
    * 接口访问路径
    */
    @ApiModelProperty(value = "接口访问路径")
    private String path;

    /**
    * 请求方式
    */
    @ApiModelProperty(value = "请求方式")
    private String method;

    /**
    * 
    */
    @ApiModelProperty(value = "")
    private String code;

    /**
    * 
    */
    @ApiModelProperty(value = "")
    private Integer pid;

    @ApiModelProperty(value = "序列号")
    private Integer sortNo;
}
