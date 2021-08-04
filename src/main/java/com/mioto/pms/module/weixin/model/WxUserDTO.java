package com.mioto.pms.module.weixin.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 获取微信用户详情参数
 * @author admin
 * @date 2021-07-15 9:45
 */
@Getter
@Setter
@ApiModel(value = "获取微信用户详情参数")
public class WxUserDTO {
    @ApiModelProperty(value = "小程序唯一标识")
    private String appid;
    @ApiModelProperty(value = "小程序的 app secret")
    private String secret;
    @ApiModelProperty(value = "登录时获取的 code")
    private String jsCode;
    @ApiModelProperty(value = "密文")
    private String encryptedData;
    @ApiModelProperty(value = "偏移量")
    private String iv;

}
