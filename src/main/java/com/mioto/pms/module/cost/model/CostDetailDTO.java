package com.mioto.pms.module.cost.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author admin
 * @date 2021-07-29 16:10
 */
@Getter
@Setter
public class CostDetailDTO {
    @ApiModelProperty(value = "子账单id - 新增不用传,删除和修改必须传")
    private Integer id;
    @ApiModelProperty(value = "费用类型id")
    private String costTypeId;
    @ApiModelProperty(value = "账单起始数据")
    private Double startData;
    @ApiModelProperty(value = "账单结束数据")
    private Double endData;
    @ApiModelProperty(value = "开始日期")
    private Date startDate;
    @ApiModelProperty(value = "结束日期")
    private Date endDate;
    @ApiModelProperty(value = "总金额")
    private Double amount;
    @ApiModelProperty(value = "修改类型 1-新增 2-修改 3-删除 如果是删除只需传id和editType字段",required = true)
    private Integer editType;
}
