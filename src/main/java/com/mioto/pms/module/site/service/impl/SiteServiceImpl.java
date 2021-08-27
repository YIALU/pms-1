package com.mioto.pms.module.site.service.impl;

import cn.hutool.core.util.StrUtil;
import com.mioto.pms.cache.RegionCache;
import com.mioto.pms.exception.BasicException;
import com.mioto.pms.module.dictionary.model.Dictionary;
import com.mioto.pms.module.site.dao.SiteDao;
import com.mioto.pms.module.site.model.Site;
import com.mioto.pms.module.site.model.SiteDTO;
import com.mioto.pms.module.site.model.SiteVO;
import com.mioto.pms.module.site.service.SiteService;
import com.mioto.pms.result.SystemTip;
import com.mioto.pms.utils.BaseUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
* @auther lzc
* date 2021-04-07 17:56:51
*/
@Service("siteService")
public class SiteServiceImpl implements SiteService {

    @Resource
    private SiteDao siteDao;
    @Resource
    private RegionCache regionCache;

    /**
    * 根据条件查询列表
    */
    @Override
    public List<SiteDTO> findList(Site site) {
        site.setUserId(BaseUtil.getLogonUserId());
        return siteDao.findList(site);
    }

    /**
    * 根据列名和对应的值查询对象
    */
    @Override
    public Site findByColumn(String column, String value) {
        return siteDao.findByColumn(column, value);
    }

    /**
    * 新增对象,忽略空值
    */
    @Override
    public int insertIgnoreNull(Site site) {
        site.setUserId(BaseUtil.getLoginUser().getId());
        return siteDao.insertIgnoreNull(site);
    }

    /**
    * 修改对象,忽略空值
    */
    @Override
    public int updateIgnoreNull(Site site) {
        return siteDao.updateIgnoreNull(site);
    }

    /**
    * 根据列名和对应的值删除对象
    */
    @Override
    public int deleteByColumn(String column, String value) {
        return siteDao.deleteByColumn(column,value);
    }

    /**
    * 根据主键列表批量删除
    */
    @Override
    public int batchDelete(String[] ids) {
        return siteDao.batchDelete(ids);
    }

    /**
     * 根据excel导入区域信息
     * @param list
     */
    @Override
    public int importExcel(List<Map<String, Object>> list) {
        List<Site> siteExcelList = new ArrayList<>(list.size());
        String province;
        String city;
        String district;
        String address;
        Site site;
        for (Map map : list) {
            province = map.get("省").toString();
            city = map.get("市").toString();
            district = map.get("区/镇").toString();
            address = map.get("详细地址").toString();
            site = new Site();
            site.setDistrictId(verify(province,city,district,address));
            site.setUserId(BaseUtil.getLoginUser().getId());
            site.setAddress(address);
            siteExcelList.add(site);
        }
        return siteDao.insertBatch(siteExcelList);
    }

    @Override
    public SiteVO findDetail(String id) {
        return siteDao.findDetail(id);
    }

    private String verify(String... args){
        emptyVerify(args);
        return dataVerify(args);
    }

    private void emptyVerify(String... args){
        for (String arg : args) {
            if (StrUtil.isEmpty(arg)){
                throw new BasicException(SystemTip.ADDRESS_EMPTY);
            }
        }
    }

    private String dataVerify(String... args){
        if (!regionCache.getProvinceMap().containsKey(args[0])){
            throw new BasicException(SystemTip.PROVINCE_NOT_EXIST,args[0] + "不存在");
        }
        String provinceId = regionCache.getProvinceMap().get(args[0]).getId();
        boolean provCityRelation = false;
        boolean cityExist = false;
        String cityId = "";
        Set<Map.Entry<String, Dictionary>> cityEntrySet = regionCache.getCityMap().entrySet();
        for (Map.Entry<String, Dictionary> cityEntry : cityEntrySet) {
            //如果市的名称存在，并且它的pid是等于省的id，说明省市的关系正确
            if (StrUtil.equals(cityEntry.getValue().getName(),args[1])){
                cityExist = true;
                if(StrUtil.equals(provinceId,cityEntry.getValue().getPid())) {
                    provCityRelation = true;
                    cityId = cityEntry.getKey();
                    break;
                }
            }
        }
        if (!cityExist){
            throw new BasicException(SystemTip.CITY_NOT_EXIST,args[1] + "不存在");
        }
        if (!provCityRelation){
            throw new BasicException(SystemTip.PROVINCE_CITY_RELATION_ERROR,args[0] + " - " + args[1] + " - " + args[2] + "对应关系不存在");
        }

        boolean provCityDistRelation = false;
        boolean districtExist = false;
        String districtId = "";
        Set<Map.Entry<String, Dictionary>> districtEntrySet = regionCache.getDistrictMap().entrySet();
        for (Map.Entry<String, Dictionary> districtEntry : districtEntrySet) {
            //如果区的名称存在，并且它的pid是等于市的id，说明省市区的关系正确
            if (StrUtil.equals(districtEntry.getValue().getName(),args[2])){
                districtExist = true;
                if(StrUtil.equals(cityId,districtEntry.getValue().getPid())) {
                    provCityDistRelation = true;
                    districtId = districtEntry.getKey();
                    break;
                }
            }
        }
        if (!districtExist){
            throw new BasicException(SystemTip.DISTRICT_NOT_EXIST,args[1] + "不存在");
        }
        if (!provCityDistRelation){
            throw new BasicException(SystemTip.PROVINCE_CITY_RELATION_ERROR,args[0] + " - " + args[1] + " - " + args[2] + "对应关系不存在");
        }
        return districtId;
    }

}
