package com.mioto.pms.module.rental.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @author admin
 * @date 2021-07-08 14:42
 */
@Getter
@Setter
@ToString
@ApiModel(value = "返回租住办理详情")
public class RentalDetailVO extends RentalInfo{
    @ApiModelProperty(value = "租户信息列表")
    private List<TenantInfo> tenantInfoList;

    @ApiModelProperty(value = "附件列表")
    private List<RentalFileVO> rentalFileList;

}
