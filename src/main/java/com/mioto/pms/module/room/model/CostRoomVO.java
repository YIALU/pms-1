package com.mioto.pms.module.room.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author admin
 * @date 2021-07-26 11:03
 */
@Getter
@Setter
@ApiModel(value = "账单房屋信息")
public class CostRoomVO {
    @ApiModelProperty(value = "房间信息")
    private String info;
    @ApiModelProperty(value = "电表id")
    private String meterElectId;
    @ApiModelProperty(value = "水表id")
    private String meterWaterId;
    @ApiModelProperty(value = "房间样式")
    private String style;
}
