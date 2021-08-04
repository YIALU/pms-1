package com.mioto.pms.module.weixin.service;

import com.mioto.pms.module.weixin.model.MiniProgramUser;
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
}