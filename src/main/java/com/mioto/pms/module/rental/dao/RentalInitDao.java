package com.mioto.pms.module.rental.dao;

import com.mioto.pms.module.rental.model.RentalInit;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author admin
 * @date 2021-08-14 14:51
 */
@Repository
public interface RentalInitDao {
    /**
     * 新增租住信息初始数据列表
     * @param rentalInitList
     * @return
     */
    int insert(List<RentalInit> rentalInitList);

    /**
     * 根据租住id查询动态数据列表
     * @param rentalId
     * @return
     */
    List<RentalInit> findList(String rentalId);

    /**
     * 修改初始数据
     * @param rentalInitList
     * @return
     */
    int update(List<RentalInit> rentalInitList);

    /**
     * 根据收费类型获取租住初始值
     * @param costType
     * @param rentalId
     * @return
     */
    Double findInitVal(@Param("costType") String costType,@Param("rentalId") String rentalId);
}
