package com.mioto.pms.module.cost.dao;

import com.mioto.pms.module.cost.model.CostDetail;
import com.mioto.pms.module.cost.model.CostDetailListVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 *
 * @author qinxj
 * @date 2021-07-16 15:05:10
 */
@Mapper
public interface CostDetailDao{

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
    CostDetail findByColumn(@Param("column") String column,@Param("value") Object value);

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
    int deleteByColumn(@Param("column") String column,@Param("value") Object value);

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
     * 根据主账单id查询动态费用纪录
     * @param costInfoId
     * @return
     */
    List<CostDetail> findDynamicListByCostInfoId(String costInfoId);

    List<CostDetailListVO> findCostDetailListVO(String costInfoId);
    /**
     * 查询未缴费账单
     * @param billNumbers
     * @return
     */
    List<CostDetail> findUnpaidFee(@Param("costType")String costType,@Param("billNumbers")String[] billNumbers);

    /**
     * 查询子账单数量
     * @param costId
     * @return
     */
    int findCountByCostId(String costId);

    int batchChangePayStatus( @Param("billNumbers")String[] billNumbers, @Param("costType") String costType);

    int editPayStatus(String[] billNumbers);
}