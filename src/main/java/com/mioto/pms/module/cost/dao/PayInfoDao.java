package com.mioto.pms.module.cost.dao;

import com.mioto.pms.module.cost.model.PayInfo;
import com.mioto.pms.module.cost.model.PayListDTO;
import com.mioto.pms.module.cost.model.PayListVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 *
 * @author qinxj
 * @date 2021-07-23 15:12:53
 */
@Mapper
public interface PayInfoDao{

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
    PayInfo findByColumn(@Param("column") String column,@Param("value") Object value);

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
    int deleteByColumn(@Param("column") String column,@Param("value") Object value);

    /**
     * 根据主键列表批量删除
     * @param ids
     * @return
     */
    int batchDelete(Integer[] ids);

    int pay(List<PayInfo> payInfoList);

    List<PayListVO> findByPager(PayListDTO payListDTO);
}