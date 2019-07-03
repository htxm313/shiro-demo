package com.yootk.ssm.util.cache.manager;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;

import java.util.Map;

public class RedisCacheManager implements CacheManager {

    //保存项目之中所有可能使用到的Cache集合对象，这些对象将通过配置文件的形式进入注入管理
    private Map<String,Cache<Object,Object>> cacheManagerMap ;
    @Override
    public Cache<Object,Object> getCache(String name) throws CacheException {
        //只要是进行缓存的处理操作，就一定要通过缓存管理来获取cache对象
        return this.cacheManagerMap.get(name);
    }

    public void setCacheManagerMap(Map<String,Cache<Object,Object>> cacheManagerMap){
        this.cacheManagerMap = cacheManagerMap ;
    }
}
