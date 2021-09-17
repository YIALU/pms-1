package com.mioto.pms.module.cost.dao;

import com.mioto.pms.module.cost.model.CostType;
import org.springframework.stereotype.Repository;

/**
 * @author admin
 * @date 2021-08-28 15:08
 */
@Repository
public interface CostTypeDao {
    CostType find(String name);

    int add(CostType costType);
}
