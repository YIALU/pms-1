package com.mioto.pms.cache;

import cn.hutool.core.util.StrUtil;
import com.mioto.pms.module.dictionary.dao.DictionaryDao;
import com.mioto.pms.module.dictionary.model.Dictionary;
import com.mioto.pms.utils.SpringBeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 区域缓存
 * @author admin
 * @date 2021-08-20 15:17
 */
@Component
@Slf4j
public class RegionCache {

    private Map<String,Dictionary> provinceMap;
    private Map<String,Dictionary> cityMap;
    private Map<String,Dictionary> districtMap;

    private static final String PROVINCE = "ADDRESS_PROVINCE";
    private static final String CITY = "ADDRESS_CITY";
    private static final String DISTRICT = "ADDRESS_DISTRICT";

    @Async
    public void init(){
        List<Dictionary> dictionaryList = SpringBeanUtil.getBean(DictionaryDao.class).findAllAddress();
        provinceMap = dictionaryList.stream()
                .filter(dictionary -> StrUtil.equals(dictionary.getType(),PROVINCE))
                .collect(Collectors.toMap(Dictionary::getName,Dictionary::get));
        cityMap = dictionaryList.stream()
                .filter(dictionary -> StrUtil.equals(dictionary.getType(),CITY))
                .collect(Collectors.toMap(Dictionary::getId,Dictionary::get));
        districtMap = dictionaryList.stream()
                .filter(dictionary -> StrUtil.equals(dictionary.getType(),DISTRICT))
                .collect(Collectors.toMap(Dictionary::getId,Dictionary::get));
        log.info("缓存所有省市区信息,省 - {} 个,市 - {} 个,区 - {} 个",provinceMap.size(),cityMap.size(),districtMap.size());
    }

    public Map<String, Dictionary> getProvinceMap() {
        return provinceMap;
    }

    public Map<String, Dictionary> getCityMap() {
        return cityMap;
    }

    public Map<String, Dictionary> getDistrictMap() {
        return districtMap;
    }
}
