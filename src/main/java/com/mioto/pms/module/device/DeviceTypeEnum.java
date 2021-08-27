package com.mioto.pms.module.device;

import cn.hutool.core.util.StrUtil;

import java.util.Arrays;
import java.util.Optional;

/**
 * @author admin
 * @date 2021-08-19 14:28
 */
public enum DeviceTypeEnum {
    ELECTRICITY_METER("caa918dfcdc14283bb009368c811ef16","电表"),
    WATER_METER("1f579c18f2ca4f5a90fff4929eb4645f","水表"),
    DEFAULT("","设备");

    public static DeviceTypeEnum getInstance(String id){
        Optional<DeviceTypeEnum> deviceTypeEnumOptional = Arrays.stream(DeviceTypeEnum.values())
                .filter(deviceTypeEnum -> StrUtil.equals(id,deviceTypeEnum.id))
                .findFirst();
        return deviceTypeEnumOptional.isPresent() ? deviceTypeEnumOptional.get() : DEFAULT;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    private String id;

    private String name;

    DeviceTypeEnum(String id, String name) {
        this.id = id;
        this.name = name;
    }
}
