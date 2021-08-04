package com.mioto.pms.module.cost.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author admin
 * @date 2021-07-21 11:42
 */
@Getter
@Setter
public class CostDetailBO extends CostInfo{

    private List<CostDetail> costDetailList;
}
