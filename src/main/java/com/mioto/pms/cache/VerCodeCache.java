package com.mioto.pms.cache;

import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.TimedCache;
import cn.hutool.core.util.StrUtil;
import org.springframework.stereotype.Component;

/**
 * 验证码缓存
 * @author admin
 * @date 2021-08-27 14:55
 */
@Component
public class VerCodeCache {
    /**
     * 创建缓存，默认5分钟过期
     */
    private TimedCache<String, String> timedCache = CacheUtil.newTimedCache(5*1000*60);

    public VerCodeCache(){
        //启动定时任务，每分钟检查一次过期
        timedCache.schedulePrune(6*1000*60);
    }

    public void put(String key,String val){
        timedCache.put(key,val);
    }

    public boolean codeEquals(String code,String phone){
        if (StrUtil.equals(code,getVal(phone))){
            timedCache.remove(phone);
            return true;
        }
        return false;
    }

    private String getVal(String key){
        if (timedCache.containsKey(key)){
            return timedCache.get(key);
        }
        return "";
    }
}
