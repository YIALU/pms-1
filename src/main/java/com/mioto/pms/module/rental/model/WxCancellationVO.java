package com.mioto.pms.module.rental.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mioto.pms.module.cost.model.CostDetailListVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

/**
 * @author admin
 * @date 2021-08-17 16:17
 */
@Getter
@Setter
@ApiModel(value = "小程序退租办理对象")
public class WxCancellationVO{

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @ApiModelProperty(value = "入住时间")
    private Date rentalTime;

    @ApiModelProperty(value = "租户列表")
    private List<TenantInfo> tenantInfoList;

    @ApiModelProperty(value = "本月子账单")
    private List<CostDetailListVO> costDetailList;
}
