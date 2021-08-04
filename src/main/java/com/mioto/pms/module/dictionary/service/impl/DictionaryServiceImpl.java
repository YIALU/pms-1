package com.mioto.pms.module.dictionary.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.mioto.pms.module.dictionary.component.SysConstant;
import com.mioto.pms.module.dictionary.dao.DictionaryDao;
import com.mioto.pms.module.dictionary.model.Dictionary;
import com.mioto.pms.module.dictionary.service.DictionaryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* @auther lzc
* date 2021-04-06 15:25:37
*/
@Service("dictionaryService")
public class DictionaryServiceImpl implements DictionaryService {

    @Resource
    private DictionaryDao dictionaryDao;

    /**
    * 根据条件查询列表
    */
    @Override
    public List<Dictionary> findList(Dictionary dictionary) {
        return dictionaryDao.findList(dictionary);
    }

    /**
    * 根据列名和对应的值查询对象
    */
    @Override
    public Dictionary findByColumn(String column, String value) {
        return dictionaryDao.findByColumn(column, value);
    }

    /**
    * 新增对象,忽略空值
    */
    @Override
    public int insertIgnoreNull(Dictionary dictionary) {
    String id = IdUtil.simpleUUID();
    dictionary.setId(id);
        dictionary.setStatus(SysConstant.STATUS_ENABLE);
        if (StrUtil.isBlank(dictionary.getPid())) {
            dictionary.setLevel(StrUtil.SLASH + dictionary.getId());
        }else {
            Map<String,Object> map = new HashMap<>(1);
            map.put("id",dictionary.getPid());
            Dictionary dict = dictionaryDao.findDictionary(map);
            dictionary.setLevel(dict.getLevel() + StrUtil.SLASH + dictionary.getId());
        }
         return dictionaryDao.insertIgnoreNull(dictionary);
    }

    /**
    * 修改对象,忽略空值
    */
    @Override
    public int updateIgnoreNull(Dictionary dictionary) {
        return dictionaryDao.updateIgnoreNull(dictionary);
    }

    /**
    * 根据列名和对应的值删除对象
    */
    @Override
    public int deleteByColumn(String column, String value) {
        return dictionaryDao.deleteByColumn(column,value);
    }

    /**
    * 根据主键列表批量删除
    */
    @Override
    public int batchDelete(String[] ids) {
        return dictionaryDao.batchDelete(ids);
    }

}
