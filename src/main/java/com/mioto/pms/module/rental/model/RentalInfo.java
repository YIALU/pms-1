package com.mioto.pms.module.rental.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
/**
 * 房屋出租信息实体
 * @author qinxj
 * @date 2021-06-30 14:53:11
 */
@Data
@ApiModel(value = "房屋出租信息")
public class RentalInfo implements Serializable{

    private static final long serialVersionUID=RentalInfo.class.getName().hashCode();
    
    /**
     * id
     */
    @ApiModelProperty(value = "id")
    private String id;
    /**
     * 房屋id
     */
    @ApiModelProperty(value = "房屋id")
    private String roomId;
    /**
     * 租住开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "租住开始时间")
    private Date startTime;
    /**
     * 租住结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "租住结束时间")
    private Date endTime;
    /**
     * 押金
     */
    @ApiModelProperty(value = "押金")
    private String deposit;
    /**
     * 出租信息状态 1-正在出租 2-历史出租纪录
     */
    @ApiModelProperty(value = "出租信息状态 1-正在出租 2-退租中 3-历史出租纪录")
    private Integer status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "创建日期")
    private Date createDate;
    
}