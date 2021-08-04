package com.mioto.pms.module.rental.service;

import com.mioto.pms.module.rental.model.*;

import java.util.List;

/**
 * IRentalInfoService
 *
 * @author qinxj
 * @date 2021-06-30 14:53:11
 */
public interface IRentalInfoService{

    /**
     * 根据条件查询列表
     * @param rentalDTO
     * @return
     */
    List<RentalListVO> findList(RentalDTO rentalDTO);

    /**
     * 根据列名和对应的值查询对象
     * @param column
     * @param value
     * @return
     */
    RentalInfo findByColumn(String column, Object value);

    /**
     * 查询详情
     * @param id
     * @return
     */
    RentalDetailVO findDetailById(String id);

    /**
     * 新增对象
     * @param rentalInfo
     * @return
     */
    int insert(RentalInfo rentalInfo);

    /**
     * 新增对象,忽略空值
     * @param rentalInfo
     * @return
     */
    int insertIgnoreNull(RentalDetailDTO rentalInfo);

    /**
     * 修改对象
     * @param rentalInfo
     * @return
     */
    int update(RentalInfo rentalInfo);

    /**
     * 修改对象,忽略空值
     * @param rentalInfo
     * @return
     */
    int updateIgnoreNull(RentalDetailDTO rentalInfo);

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
    int batchDelete(String[] ids);

    /**
     * 根据房屋id查询和出租状态查询出租信息
     * @param roomId
     * @return
     */
    List<RentalInfo> findByRoomIdAndStatus(String roomId,int status);

    CancellationVO findCancellation(String rentalId);
}