package com.mioto.pms.module.statistics.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author admin
 * @date 2021-09-04 14:29
 */
@Getter
@Setter
public class EnergyBO {
    private Integer logonUserId;
    private Date beginDate;
    private Date endDate;
    private String tableName;
    private int type;

    private String wildcard;
}
