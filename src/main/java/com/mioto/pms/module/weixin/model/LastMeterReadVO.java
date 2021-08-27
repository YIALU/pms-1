package com.mioto.pms.module.weixin.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author admin
 * @date 2021-08-12 17:16
 */
@Getter
@Setter
@ApiModel(value = "上月抄表数据列表")
public class LastMeterReadVO {
    @ApiModelProperty(value = "上月表数据")
    private Double lastData;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @ApiModelProperty(value = "上月抄表时间")
    private Date lastTime;
    @ApiModelProperty(value = "费用类型名")
    private String name;

    @ApiModelProperty(value = "费用类型")
    private String costType;

    public LastMeterReadVO get(){
        return this;
    }
}
