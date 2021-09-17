package com.mioto.pms.module.device;

import cn.hutool.core.util.StrUtil;
import com.mioto.pms.module.price.PricingStrategyEnum;

import java.util.Arrays;
import java.util.Optional;

/**
 * @author admin
 * @date 2021-08-19 14:28
 */
public enum DeviceTypeEnum {
    ELECTRICITY_METER("caa918dfcdc14283bb009368c811ef16","电表","am_meter_data",PricingStrategyEnum.ELECTRICITY_FEE),
    WATER_METER("1f579c18f2ca4f5a90fff4929eb4645f","水表","water_meter_data",PricingStrategyEnum.WATER_FEE),
    DEFAULT("","设备","",PricingStrategyEnum.OTHERS);

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

    public String getTableName() {
        return tableName;
    }

    public PricingStrategyEnum getPricingStrategyEnum() {
        return pricingStrategyEnum;
    }

    private String id;

    private String name;

    private String tableName;

    private PricingStrategyEnum pricingStrategyEnum;

    DeviceTypeEnum(String id, String name,String tableName,PricingStrategyEnum pricingStrategyEnum) {
        this.id = id;
        this.name = name;
        this.tableName = tableName;
        this.pricingStrategyEnum = pricingStrategyEnum;
    }
}
