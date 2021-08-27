package com.mioto.pms.module.cost.service;

import com.mioto.pms.module.cost.model.*;
import com.mioto.pms.module.weixin.model.ManualMeterReadDTO;

import java.util.List;

/**
 * ICostInfoService
 *
 * @author qinxj
 * @date 2021-07-16 15:05:11
 */
public interface ICostInfoService{


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
    CostInfo findByColumn(String column, Object value);

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
    int deleteByColumn(String column, Object value);

    /**
     * 根据主键列表批量删除
     * @param ids
     * @return
     */
    int batchDelete(Integer[] ids);

    List<CostListVO> findCostList(CostListDTO costListDTO,boolean isALl);

    void insertDetail(String roomId,boolean firstInsert);

    int batchSend(String[] costInfoIds);

    /**
     * 查询账单详情
     * @param costInfoId
     * @return
     */
    CostDetailVO findDetail(String costInfoId);

    /**
     * 批量修改支付状态
     * @param billNumber
     * @return
     */
    int batchChangePayStatus(String[] billNumber);
    /**
     * 根据账单编号列表查询对应账单缴费状态
     * @param billNumbers
     * @return
     */
    List<CostInfo> findByBillNumbers(String[] billNumbers);

    /**
     * 修改主账单总金额
     * @param id
     * @return
     */
    int updateAmount(String id);

    /**
     * 手动抄表
     * @param meterReadDTOList
     */
    int manualMeterRead(ManualMeterReadDTO meterReadDTOList);
}