package com.mioto.pms.module.rental.dao;

import com.mioto.pms.module.rental.model.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 *
 * @author qinxj
 * @date 2021-06-30 14:53:11
 */
@Mapper
public interface RentalInfoDao{

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
    RentalInfo findByColumn(@Param("column") String column,@Param("value") Object value);

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
    int insertIgnoreNull(RentalInfo rentalInfo);

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
    int updateIgnoreNull(RentalInfo rentalInfo);

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
    int batchDelete(String[] ids);

    RentalDetailVO findDetailById(String id);

    List<RentalInfo> findByRoomIdAndStatus(@Param("roomId")String roomId, @Param("status")int status);

    CancellationVO findCancellation(String rentalId);
}