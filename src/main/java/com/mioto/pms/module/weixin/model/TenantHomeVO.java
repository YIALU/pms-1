package com.mioto.pms.module.weixin.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author admin
 * @date 2021-08-10 14:25
 */
@Getter
@Setter
@ApiModel(value = "租户登录成功，主界面数据")
public class TenantHomeVO {
    @ApiModelProperty(value = "房间id")
    private String roomId;
    @ApiModelProperty(value = "租住id")
    private String rentalId;
    @ApiModelProperty(value = "租户姓名")
    private String tenantName;
    @ApiModelProperty(value = "证件号")
    private String idCard;
    @ApiModelProperty(value = "电话")
    private String phone;
    @ApiModelProperty(value = "区域")
    private String address;
    @ApiModelProperty(value = "房间名")
    private String roomName;
    @ApiModelProperty(value = "房东openid")
    private String landlordOpenId;
    @ApiModelProperty(value = "未缴费账单列表")
    private List<TenantBillVO> unpaidFeesVOList;


}
