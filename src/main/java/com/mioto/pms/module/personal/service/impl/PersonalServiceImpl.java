package com.mioto.pms.module.personal.service.impl;

import com.mioto.pms.module.personal.dao.PersonalDao;
import com.mioto.pms.module.personal.model.Personal;
import com.mioto.pms.module.personal.model.PersonalDTO;
import com.mioto.pms.module.personal.service.PersonalService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
* @auther lzc
* date 2021-04-08 18:00:58
*/
@Service("personalService")
public class PersonalServiceImpl implements PersonalService {

    @Resource
    private PersonalDao personalDao;

    /**
    * 根据条件查询列表
    */
    @Override
    public List<PersonalDTO> findList(Personal personal) {
        return personalDao.findList(personal);
    }

    /**
    * 根据列名和对应的值查询对象
    */
    @Override
    public Personal findByColumn(String column, String value) {
        return personalDao.findByColumn(column, value);
    }

    /**
    * 新增对象,忽略空值
    */
    @Override
    public int insertIgnoreNull(Personal personal) {

         return personalDao.insertIgnoreNull(personal);
    }

    /**
    * 修改对象,忽略空值
    */
    @Override
    public int updateIgnoreNull(Personal personal) {
        return personalDao.updateIgnoreNull(personal);
    }

    /**
    * 根据列名和对应的值删除对象
    */
    @Override
    public int deleteByColumn(String column, String value) {
        return personalDao.deleteByColumn(column,value);
    }

    /**
    * 根据主键列表批量删除
    */
    @Override
    public int batchDelete(String[] ids) {
        return personalDao.batchDelete(ids);
    }

    @Override
    public List<Personal> findOwmer() {
        return personalDao.findOwner();
    }

}
