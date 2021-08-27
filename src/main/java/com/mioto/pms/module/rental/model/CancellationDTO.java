package com.mioto.pms.module.rental.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @author admin
 * @date 2021-08-18 14:09
 */
@Getter
@Setter
@ToString
@ApiModel(value = "退租办理对象")
public class CancellationDTO {

    @ApiModelProperty(value = "租住id")
    private String rentalId;

    @ApiModelProperty(value = "退租数据")
    private List<CancellationDataDTO> cancellationDataDTOList;
}
