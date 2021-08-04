package com.mioto.pms.module.user.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author admin
 * @date 2021-07-28 14:57
 */
@Getter
@Setter
@ApiModel(value = "查询小程序用户列表对象")
public class MiniProgramUserListVO {
    @ApiModelProperty(value = "用户id")
    private Integer id;

    @ApiModelProperty(value = "微信昵称")
    private String wxNickname;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "房间信息")
    private String roomInfo;

    @ApiModelProperty(value = "房东")
    private String landlord;

    @ApiModelProperty(value = "状态 1-在租 ")
    private Integer status;
}
