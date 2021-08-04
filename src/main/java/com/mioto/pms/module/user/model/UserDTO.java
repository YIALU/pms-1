package com.mioto.pms.module.user.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author admin
 * @date 2021-07-14 15:18
 */
@Getter
@Setter
public class UserDTO extends User{
    @ApiModelProperty(value = "角色id")
    private Integer roleId;
}
