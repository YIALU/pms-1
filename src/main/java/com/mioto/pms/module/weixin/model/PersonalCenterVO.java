package com.mioto.pms.module.weixin.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @author admin
 * @date 2021-08-17 14:08
 */
@Getter
@Setter
@ApiModel(value = "个人中心数据")
@ToString
public class PersonalCenterVO {
    @ApiModelProperty(value = "收款总额")
    private Double amount;
    @ApiModelProperty(value = "未收费账单")
    private List<UnpaidFeeBillsVO> unpaidFeeBillsVOList;
}
