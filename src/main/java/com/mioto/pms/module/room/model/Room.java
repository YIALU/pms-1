package com.mioto.pms.module.room.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mioto.pms.utils.BaseUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
* @auther lzc
* date 2021-04-28 15:49:17
*/
@Data
@ApiModel(value = "开户配表")
public class Room implements Serializable {
    private static final long serialVersionUID= Room.class.getName().hashCode();

    /**
    * id
    */
    @ApiModelProperty(value = "id")
    private String id;
    /**
    * 房间名称或编号
    */
    @ApiModelProperty(value = "房间名称或编号")
    private String roomName;

    /**
    * 房间样式
    */
    @ApiModelProperty(value = "房间样式")
    private String style;

    /**
    * 业主id
    */
    @ApiModelProperty(value = "业主id")
    private Integer personalId;

    /**
    * 电话
    */
    @ApiModelProperty(value = "电话")
    private String phone;

    /**
    * 电表抄表策略
    */
    @ApiModelProperty(value = "电表抄表策略")
    private Integer meterElect;

    /**
    * 水表抄表策略
    */
    @ApiModelProperty(value = "水表抄表策略 -- 遗弃字段")
    private Integer meterWater;

    /**
    * 欠费策略
    */
    @ApiModelProperty(value = "欠费策略")
    private Integer arrearsId;

    /**
    * 房东id
    */
    @ApiModelProperty(value = "房东id")
    private Integer userId;

    @ApiModelProperty(value = "区域id")
    private Integer siteId;

    @ApiModelProperty (value = "创建时间")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
}
