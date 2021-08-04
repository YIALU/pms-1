package com.mioto.pms.module.cost.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @author admin
 * @date 2021-07-29 14:48
 */
@Getter
@Setter
@ApiModel(value = "编辑账单详情-子账单对象")
public class EditCostDetailDTO {

    @NotEmpty(message="主账单id不能为空")
    @ApiModelProperty(value = "主账单id",required = true)
    private String id;
    @NotEmpty(message="主账单编号不能为空")
    @ApiModelProperty(value = "主账单编号",required = true)
    private String billNumber;
    @ApiModelProperty(value = "子账单列表",required = true)
    private List<CostDetailDTO> costDetailDTOList;
}
