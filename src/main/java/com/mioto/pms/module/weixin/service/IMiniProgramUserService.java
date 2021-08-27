package com.mioto.pms.module.weixin.service;

import com.mioto.pms.module.weixin.model.*;

import java.util.List;

/**
 * IMiniProgramUserService
 *
 * @author qinxj
 * @date 2021-07-16 10:23:21
 */
public interface IMiniProgramUserService{

    /**
     * 根据条件查询列表
     * @param miniProgramUser
     * @return
     */
    List<MiniProgramUser> findList(MiniProgramUser miniProgramUser);

    /**
     * 根据列名和对应的值查询对象
     * @param column
     * @param value
     * @return
     */
    MiniProgramUser findByColumn(String column, Object value);

    /**
     * 新增对象
     * @param miniProgramUser
     * @return
     */
    int insert(MiniProgramUser miniProgramUser);

    /**
     * 新增对象,忽略空值
     * @param miniProgramUser
     * @return
     */
    int insertIgnoreNull(MiniProgramUser miniProgramUser);

    /**
     * 修改对象
     * @param miniProgramUser
     * @return
     */
    int update(MiniProgramUser miniProgramUser);

    /**
     * 修改对象,忽略空值
     * @param miniProgramUser
     * @return
     */
    int updateIgnoreNull(MiniProgramUser miniProgramUser);

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

    MiniProgramUser bindPhone(String phone,String openId,String nickName);


    List<TenantHomeVO> findHomeData();

    /**
     * 查询正在出租房屋未缴费账单列表
     * @return
     */
    List<UnpaidFeesVO> findUnpaidFees(String rentalId);

    /**
     * 查询合同信息
     * @param rentalId
     * @return
     */
    List<ContractInfoVO> findContractInfo(String rentalId);

    /**
     * 查询历史账单列表
     * @param rentalId
     * @return
     */
    List<TenantBillVO> findHistoryBills(String rentalId);

    /**
     * 查询房东下面的租户列表
     * @return
     */
    List<TenantListVO> findTenantList();

    /**
     * 查询上月抄表数据
     * @param rentalId
     * @return
     */
    List<LastMeterReadVO> findLastData(String rentalId);

    /**
     * 个人中心数据查询
     * @param date
     * @return
     */
    PersonalCenterVO getPersonalCenterData(String date);
}