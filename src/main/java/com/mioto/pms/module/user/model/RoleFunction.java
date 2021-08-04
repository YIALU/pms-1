package com.mioto.pms.module.user.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author lizhicai
 * @description
 * @date 2021/4/14
 */
@Data
public class RoleFunction {


    @ApiModelProperty(value = "主键id")
    private int id;


    @ApiModelProperty(value = "角色id")
    private Integer roleId;

    @ApiModelProperty(value = "权限id")
    private String functionId;

    @ApiModelProperty(value = "状态")
    private Integer state;


}
