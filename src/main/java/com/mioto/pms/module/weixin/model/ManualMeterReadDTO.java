package com.mioto.pms.module.weixin.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.util.Map;

/**
 * @author admin
 * @date 2021-08-12 17:42
 */
@Getter
@Setter
@ApiModel(value = "手动抄表对象")
public class ManualMeterReadDTO {
    @ApiModelProperty(value = "租住id")
    @NotEmpty(message = "租住id不能为空")
    private String rentalId;
    @ApiModelProperty(value = "费用数据 key - 费用类型 ,value - 抄表数据")
    @NotEmpty(message = "费用数据不能为空")
    private Map<String,Double> costMap;
}
