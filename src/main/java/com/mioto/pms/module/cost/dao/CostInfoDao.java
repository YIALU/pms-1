package com.mioto.pms.module.cost.dao;

import com.mioto.pms.module.cost.model.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 *
 * @author qinxj
 * @date 2021-07-16 15:05:11
 */
@Mapper
public interface CostInfoDao{

    /**
     * 根据条件查询列表
     * @param costInfo
     * @return
     */
    List<CostInfo> findList(CostInfo costInfo);

    /**
     * 根据列名和对应的值查询对象
     * @param column
     * @param value
     * @return
     */
    CostInfo findByColumn(@Param("column") String column,@Param("value") Object value);

    /**
     * 新增对象
     * @param costInfo
     * @return
     */
    int insert(CostInfo costInfo);

    /**
     * 新增对象,忽略空值
     * @param costInfo
     * @return
     */
    int insertIgnoreNull(CostInfo costInfo);

    /**
     * 修改对象
     * @param costInfo
     * @return
     */
    int update(CostInfo costInfo);

    /**
     * 修改对象,忽略空值
     * @param costInfo
     * @return
     */
    int updateIgnoreNull(CostInfo costInfo);

    /**
     * 根据列名和对应的值删除对象
     * @param column
     * @param value
     * @return
     */
    int deleteByColumn(@Param("column") String column,@Param("value") Object value);

    /**
     * 根据主键列表批量删除
     * @param ids
     * @return
     */
    int batchDelete(Integer[] ids);

    /**
     * 查询最近一条账单信息
     * @return
     */
    CostDetailBO findLastCost(String rentalId);

    List<CostListVO> findCostList(CostListDTO costListDTO);

    /**
     * 查询账单详情
     * @param costInfoId
     * @return
     */
    CostDetailVO findDetail(String costInfoId);

    int batchSend(String[] costInfoIds);

    int batchChangePayStatus(String[] billNumbers);

    /**
     * 根据账单编号列表查询对应账单缴费状态
     * @param billNumbers
     * @return
     */
    List<CostInfo> findByBillNumbers(String[] billNumbers);

    int updateAmount(String id);
}