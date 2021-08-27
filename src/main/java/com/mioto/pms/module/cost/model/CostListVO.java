package com.mioto.pms.module.cost.model;

import cn.hutool.core.collection.CollUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author admin
 * @date 2021-07-22 15:36
 */
@Getter
@Setter
@ApiModel(value = "账单列表对象")
public class CostListVO {
    @ApiModelProperty(value = "主账单id")
    private String id;
    @ApiModelProperty(value = "主账单编号")
    private String billNumber;
    @ApiModelProperty(value = "主账单金额")
    private Double totalAmount;
    @ApiModelProperty(value = "租户姓名")
    private String tenantName;
    @ApiModelProperty(value = "租户电话")
    private String tenantPhone;
    @ApiModelProperty(value = "租房地址")
    private String address;
    @ApiModelProperty(value = "账单是否发送")
    private Integer isSend;
    @ApiModelProperty(value = "子账单列表")
    private List<CostDetailListVO> costDetailListVOList;

    public List<CostDetailListVO> getCostDetailListVOList() {
        if (CollUtil.isNotEmpty(costDetailListVOList)){
            costDetailListVOList.sort(CostDetailListVO::compareTo);
        }
        return costDetailListVOList;
    }
}
