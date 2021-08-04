package com.mioto.pms.module.rental.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author admin
 * @date 2021-07-08 16:04
 */
@Getter
@Setter
@ToString
public class RentalFileVO {
    @ApiModelProperty(value = "文件id")
    private String fileId;
    @ApiModelProperty(value = "附件类型 1-合同 2-身份证")
    private Integer type;
    @ApiModelProperty(value = "文件名")
    private String name;
}
