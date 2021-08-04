package com.mioto.pms.module.rental.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author admin
 * @date 2021-07-06 16:47
 */
@Getter
@Setter
@ToString
@ApiModel(value = "房屋出租查询条件")
public class RentalDTO {

    @ApiModelProperty(value = "区域id")
    private String siteId;
    @ApiModelProperty(value = "租户姓名/号码")
    private String tenantInfo;
    @ApiModelProperty(value = "用户id")
    private Integer userId;
}
