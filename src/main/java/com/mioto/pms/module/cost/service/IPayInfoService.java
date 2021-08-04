package com.mioto.pms.module.cost.service;

import com.mioto.pms.module.cost.model.PayInfo;
import com.mioto.pms.module.cost.model.PayListDTO;
import com.mioto.pms.module.cost.model.PayListVO;

import java.util.List;

/**
 * IPayInfoService
 *
 * @author qinxj
 * @date 2021-07-23 15:12:53
 */
public interface IPayInfoService{
    /**
     * 现金支付
     */
    int PAY_TYPE_CASH = 2;

    /**
     * 根据条件查询列表
     * @param payInfo
     * @return
     */
    List<PayInfo> findList(PayInfo payInfo);

    /**
     * 根据列名和对应的值查询对象
     * @param column
     * @param value
     * @return
     */
    PayInfo findByColumn(String column, Object value);

    /**
     * 新增对象
     * @param payInfo
     * @return
     */
    int insert(PayInfo payInfo);

    /**
     * 新增对象,忽略空值
     * @param payInfo
     * @return
     */
    int insertIgnoreNull(PayInfo payInfo);

    /**
     * 修改对象
     * @param payInfo
     * @return
     */
    int update(PayInfo payInfo);

    /**
     * 修改对象,忽略空值
     * @param payInfo
     * @return
     */
    int updateIgnoreNull(PayInfo payInfo);

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
     * 小程序现金支付
     * @param billNumbers
     * @return
     */
    int miniProgramCashPay(String[] billNumbers);

    /**
     * web现金支付
     * @param costType
     * @param billNumbers
     * @return
     */
    int webCashPay(String costType,String[] billNumbers);

    /**
     * 分页查询缴费纪录
     * @param payListDTO
     * @return
     */
    List<PayListVO> findByPager(PayListDTO payListDTO);
}