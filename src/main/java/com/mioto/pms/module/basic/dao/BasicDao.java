package com.mioto.pms.module.basic.dao;

import com.mioto.pms.module.basic.model.Scheduler;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author admin
 * @date 2021-09-04 15:53
 */
@Repository
public interface BasicDao {

    int saveScheduler(Scheduler scheduler);

    int removeScheduler(String schedulerId);

    List<Scheduler> findListByType(String schedulerType);
}
