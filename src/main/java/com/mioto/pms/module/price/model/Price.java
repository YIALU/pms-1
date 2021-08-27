package com.mioto.pms.module.price.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
* @auther lzc
* date 2021-04-16 14:51:24
*/
@Data
@ApiModel(value = "费用策略")
public class Price implements Serializable {
    private static final long serialVersionUID= Price.class.getName().hashCode();

    /**
    * id
    */
    @ApiModelProperty(value = "id")
    private String id;
    /**
    * 序号
    */
    @ApiModelProperty(value = "序号")
    private String number;

    /**
    * 策略名称
    */
    @ApiModelProperty(value = "策略名称")
    private String name;

    /**
    * 策略类型
    */
    @ApiModelProperty(value = "策略类型")
    private String type;

    /**
     * 策略类型
     */
    @ApiModelProperty(value = "单价")
    private Double unitPrice;


    /**
    * 0:关闭,1:启用
    */
    @ApiModelProperty(value = "0:关闭,1:启用")
    private Integer status;

    /**
    * 创建人
    */
    @ApiModelProperty(value = "房东id")
    private Integer userId;

    /**
    * 创建时间
    */
    @ApiModelProperty(value = "创建时间")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date creationTime;

    public Price get(){
        return this;
    }

}
