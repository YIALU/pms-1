package com.mioto.pms.module.price.model;

import lombok.Getter;
import lombok.Setter;

/**
 * 收费策略
 * @author admin
 * @date 2021-07-06 17:24
 */
@Getter
@Setter
public class PricingStrategy {
    /**
     * 费用
     */
    private Double fee;

    /**
     * 类型 - 对应 PricingStrategyEnum
     */
    private String type;
}
