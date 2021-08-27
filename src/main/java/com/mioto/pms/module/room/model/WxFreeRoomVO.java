package com.mioto.pms.module.room.model;

import com.mioto.pms.module.site.model.SiteBO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author admin
 * @date 2021-08-09 17:18
 */
@Data
@ApiModel(value = "小程序空闲房源对象")
public class WxFreeRoomVO {
    @ApiModelProperty(value = "房间id")
    private String roomId;
    @ApiModelProperty(value = "房间详细地址")
    private SiteBO siteBO;
    @ApiModelProperty(value = "房租")
    private Double rent;
    @ApiModelProperty(value = "房间样式")
    private String roomStyle;
    @ApiModelProperty(value = "房间名称")
    private String roomName;
}
