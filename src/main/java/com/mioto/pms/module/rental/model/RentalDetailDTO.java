package com.mioto.pms.module.rental.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @author admin
 * @date 2021-07-08 10:41
 */
@Getter
@Setter
@ToString
@ApiModel(value = "新增租住办理")
public class RentalDetailDTO extends RentalInfo{
    @ApiModelProperty(value = "租户信息列表")
    private List<TenantInfo> tenantInfoList;
    @ApiModelProperty(value = "租住附件列表")
    private List<RentalFile> rentalFileList;
}
