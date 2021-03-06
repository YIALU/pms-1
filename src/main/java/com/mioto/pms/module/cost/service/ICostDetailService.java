package com.mioto.pms.module.cost.service;

import com.mioto.pms.module.cost.model.CostDetail;
import com.mioto.pms.module.cost.model.CostDetailListVO;
import com.mioto.pms.module.cost.model.EditCostDetailDTO;

import java.util.List;

/**
 * ICostDetailService
 *
 * @author qinxj
 * @date 2021-07-16 15:05:10
 */
public interface ICostDetailService{
    /**
     * 账单生成类型 1-系统生成  2-手动添加
     */
    int BILL_TYPE_GEN = 1;
    int BILL_TYPE_NON_GEN = 2;

    /**
     * 根据条件查询列表
     * @param costDetail
     * @return
     */
    List<CostDetail> findList(CostDetail costDetail);

    /**
     * 根据列名和对应的值查询对象
     * @param column
     * @param value
     * @return
     */
    CostDetail findByColumn(String column, Object value);

    /**
     * 新增对象
     * @param costDetail
     * @return
     */
    int insert(CostDetail costDetail);

    /**
     * 新增对象,忽略空值
     * @param costDetail
     * @return
     */
    int insertIgnoreNull(CostDetail costDetail);

    /**
     * 修改对象
     * @param costDetail
     * @return
     */
    int update(CostDetail costDetail);

    /**
     * 修改对象,忽略空值
     * @param costDetail
     * @return
     */
    int updateIgnoreNull(CostDetail costDetail);

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

    /**
     * 根据租住办理id查询最近一次费用产生纪录
     * @param rentalId
     * @return
     */
    List<CostDetail> findByRentalId(String rentalId);
    /**
     * 查询未缴费账单
     * @param billNumbers
     * @return
     */
    List<CostDetail> findUnpaidFee(String costType,String[] billNumbers);

    int batchEdit(EditCostDetailDTO editCostDetailDTO);

    /**
     * 根据主账单编号批量修改子账单支付状态
     * @param billNumbers
     * @return
     */
    int batchChangePayStatus(String[] billNumbers,String costType);

    /**
     * 根据子账单编号批量修改子账单支付状态
     * @param billNumbers
     * @return
     */
    int editPayStatus(String[] billNumbers);

    /**
     * 根据主账单id查询子账单列表
     * @param costInfoId
     * @return
     */
    List<CostDetailListVO> findListByCostInfoId(String costInfoId);

}