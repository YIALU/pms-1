package com.mioto.pms.module.room.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author lizhicai
 * @description
 * @date 2021/4/29
 */
@Data
@ApiModel(value = "分页返回")
public class RoomListVO {
    @ApiModelProperty(value = "房间id")
    private String id;

    @ApiModelProperty(value = "房间名称")
    private String roomName;

    @ApiModelProperty(value = "电表设备id")
    private String elect;

    @ApiModelProperty(value = "水表设备id")
    private String water;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "区域")
    private String address;
}
