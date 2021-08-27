package com.mioto.pms.module.weixin.dao;

import com.mioto.pms.module.weixin.model.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 *
 * @author qinxj
 * @date 2021-07-16 10:23:21
 */
@Mapper
public interface MiniProgramUserDao{

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
    MiniProgramUser findByColumn(@Param("column") String column,@Param("value") Object value);

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
    int deleteByColumn(@Param("column") String column,@Param("value") Object value);

    /**
     * 根据主键列表批量删除
     * @param ids
     * @return
     */
    int batchDelete(Integer[] ids);

    List<TenantHomeVO> findHomeData(Integer logonUserId);

    List<TenantBillVO> findUnpaidFeesVOList(String rentalId);

    List<UnpaidFeesVO> findUnpaidFees(String rentalId);

    List<ContractInfoVO> findContractInfo(String rentalId);

    List<TenantBillVO> findHistoryBills(String rentalId);

    List<TenantListVO> findTenantList(Integer logonUserId);

    List<LastMeterReadVO> findLastData(String rentalId);

    PersonalCenterVO findPersonalCenterData(@Param("date") String date, @Param("logonUserId")Integer logonUserId);

    List<UnpaidFeeBillsVO> findUnpaidFeeBillsVO(@Param("date") String date, @Param("logonUserId")Integer logonUserId);
}