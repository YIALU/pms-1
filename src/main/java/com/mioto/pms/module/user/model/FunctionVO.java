package com.mioto.pms.module.user.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author admin
 * @date 2021-07-14 16:35
 */
@Getter
@Setter
public class FunctionVO extends Function{
    @ApiModelProperty(value = "是否有权限 1-有权限 0-无权限")
    private Integer selected;
}
