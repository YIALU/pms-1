package com.mioto.pms.module.weixin.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author admin
 * @date 2021-08-12 16:05
 */
@Getter
@Setter
@ApiModel(value = "租户列表")
public class TenantListVO {
    @ApiModelProperty(value = "租户名")
    private String name;
    @ApiModelProperty(value = "电话号码")
    private String phone;
    @ApiModelProperty(value = "房间地址")
    private String address;
    @ApiModelProperty(value = "费用类型")
    private String costType;
}
