package com.mioto.pms.module.rental.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author admin
 * @date 2021-07-08 15:37
 */
@Getter
@Setter
@ApiModel(value = "房屋出租附件信息")
public class RentalFile {
    @ApiModelProperty(value = "主键id")
    private Integer id;
    @ApiModelProperty(value = "房屋出租信息id")
    private String rentalId;
    @ApiModelProperty(value = "文件id")
    private String fileId;
    @ApiModelProperty(value = "附件类型 1-合同 2-身份证")
    private Integer type;
}
