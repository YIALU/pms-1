package com.mioto.pms.module.weixin.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

/**
 * @author admin
 * @date 2021-08-11 11:53
 */
@Getter
@Setter
@ApiModel(value = "合同信息")
public class ContractInfoVO {
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @ApiModelProperty(value = "合同开始时间")
    private Date startTime;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @ApiModelProperty(value = "合同结束时间")
    private Date endTime;
    @ApiModelProperty(value = "押金")
    private Double deposit;


    @ApiModelProperty(value = "合同收费列表")
    private List<ContractPriceVO> contractPriceVOList;
}
