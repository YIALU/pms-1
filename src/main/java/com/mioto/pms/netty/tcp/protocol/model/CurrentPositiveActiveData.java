package com.mioto.pms.netty.tcp.protocol.model;

import lombok.Getter;
import lombok.Setter;

/**
 * @author admin
 * @date 2021-09-01 17:26
 */
@Getter
@Setter
public class CurrentPositiveActiveData extends PacketData{
    private double totalElectricEnergy;
    private String deviceType;
}
