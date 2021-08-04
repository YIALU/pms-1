package com.mioto.pms.module.statistics.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author admin
 * @date 2021-07-29 17:13
 */
@Getter
@Setter
@ApiModel("房屋信息统计对象")
public class RoomInfoStatisticsVO {

    @ApiModelProperty("房间数量")
    private Integer roomCount;
    @ApiModelProperty("在租房间数量")
    private Integer rentalRoomCount;
    @ApiModelProperty("空闲房间数量")
    private Integer freeRoomCount;

    @ApiModelProperty("水表数量")
    private Integer meterWaterCount;
    @ApiModelProperty("电表数量")
    private Integer meterElectCount;

    public Integer getFreeRoomCount(){
        return roomCount - rentalRoomCount;
    }
}
