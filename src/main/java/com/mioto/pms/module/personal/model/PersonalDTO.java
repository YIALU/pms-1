package com.mioto.pms.module.personal.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author lizhicai
 * @description
 * @date 2021/4/8
 */
@Data
public class PersonalDTO extends Personal {

    @ApiModelProperty(value = "人员类型名称")
    private String PersonalTypeName;
}
