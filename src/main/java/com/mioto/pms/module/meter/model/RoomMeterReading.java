package com.mioto.pms.module.meter.model;

import lombok.Getter;
import lombok.Setter;

/**
 * 房屋抄表策略
 * @author admin
 * @date 2021-07-17 14:33
 */
@Getter
@Setter
public class RoomMeterReading extends MeterReading{

    /**
     * 房屋id
     */
    private String roomId;
}
