package com.mioto.pms.module.basic.model;

import lombok.Getter;
import lombok.Setter;

/**
 * @author admin
 * @date 2021-09-04 15:50
 */
@Getter
@Setter
public class Scheduler {
    private String id;
    private String schedulerType;
    private String schedulerParam;
    private String schedulerTime;
}
