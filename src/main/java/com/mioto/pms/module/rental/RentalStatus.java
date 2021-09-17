package com.mioto.pms.module.rental;

/**
 * @author admin
 * @date 2021-08-27 18:14
 */
public class RentalStatus {
    /**
     * 正在出租
     */
    public static final int STATUS_RENTING = 1;
    /**
     * 退租中
     */
    public static final int STATUS_CANCEL = 2;
    /**
     * 历史出租纪录
     */
    public static final int STATUS_HISTORY = 3;
}
