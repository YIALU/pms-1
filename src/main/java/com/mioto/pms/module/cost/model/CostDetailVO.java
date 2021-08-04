package com.mioto.pms.module.cost.model;

import com.mioto.pms.module.room.model.CostRoomVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author admin
 * @date 2021-07-26 10:59
 */
@Getter
@Setter
@ApiModel(value = "账单详情")
public class CostDetailVO {
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
    @ApiModelProperty(value = "房间信息")
    private CostRoomVO costRoomVO;
    @ApiModelProperty(value = "子账单列表")
    private List<CostDetailListVO> costDetailListVOList;
}
