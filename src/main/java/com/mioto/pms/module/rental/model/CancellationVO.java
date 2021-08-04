package com.mioto.pms.module.rental.model;

import com.mioto.pms.module.cost.model.CostDetailListVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


/**
 * @author admin
 * @date 2021-07-27 17:57
 */
@Getter
@Setter
@ApiModel(value = "退租办理对象")
public class CancellationVO {
    @ApiModelProperty(value = "租户姓名")
    private String tenantName;
    @ApiModelProperty(value = "证件号码")
    private String idCard;

    @ApiModelProperty(value = "本月子账单")
    private List<CostDetailListVO> costDetailList;
}
