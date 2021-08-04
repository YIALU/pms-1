package com.mioto.pms.module.price;

import cn.hutool.core.util.StrUtil;

/**
 * 收费策略枚举
 * @author admin
 * @date 2021-07-06 16:18
 */
public enum PricingStrategyEnum {
    /**
     * 房租
     */
    RENT_FEE("05bd8b696c0b4c93b8b745e02b7694c7",2),
    /**
     * 网络费
     */
    NETWORK_FEE("0cb556c519b34e048b1da4ec3b02f953",2),
    /**
     * 暖气费
     */
    HEATING_FEE("94cc76bec3f242929fe15ff3c6f5e5ee",1),
    /**
     * 水费
     */
    WATER_FEE("a488c3d4403e4e809fc6d05b67563210",1),
    /**
     * 物业费
     */
    PROPERTY_FEE("b810698185d24e4aa730921069d8e153",2),
    /**
     * 电费
     */
    ELECTRICITY_FEE("bd1e9795578d4771b81d7027e807ebcc",1),
    /**
     * 停车费
     */
    PARKING_FEE("bee1b61b23a84b219181838fe0c13b63",2),
    /**
     * 燃气费
     */
    GAS_FEE("45e6f75ab9114cc994b584e49eb352e7",1),
    /**
     * 热水费
     */
    HOT_WATER_FEE("0b37e5954b4e48349382f247c37aee36",1),
    /**
     * 卫生费
     */
    HEALTH_FEE("fbd4262ebe4046c6bc4d7e29b7179436",2);

    public static PricingStrategyEnum getInstance(String id){
        for (PricingStrategyEnum value : PricingStrategyEnum.values()) {
            if (StrUtil.equals(value.id,id)){
                return value;
            }
        }
        throw new RuntimeException("该费用类型不支持");
    }

    public int getType() {
        return type;
    }

    public String getId() {
        return id;
    }

    /**
     * 费用类型id - 对应数据字典id
     */
    private String id;
    /**
     * 费用类型 1-按使用量收费 2-固定费用
     */
    private int type;

    PricingStrategyEnum(String id,int type){
        this.id = id;
        this.type = type;
    }
}
