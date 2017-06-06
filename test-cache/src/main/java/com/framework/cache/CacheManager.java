package com.framework.cache;


import com.framework.cache.serializer.Serializer;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by qinshucai on 2016/4/6.
 */
public class CacheManager {
    private final static String cacheServiceLock ="cacheServiceLock";
    private final static String cacheManagerLock ="cacheManagerLock";
    private final static Map<String,String> cacheProviders = new HashMap<String,String>();
    private final static Map<String,CacheService> cacheServiceMap = new HashMap<String,CacheService>();
    private static CacheManager instance;

    public static final String CACHE_PROVIDER_REDIS = "redis";

    private CacheManager()
    {
        cacheProviders.put("redis","com.framework.cache.provider.redis.RedisCacheServiceImpl");
    }

    public static CacheManager getInstance()
    {
        if(instance == null)
        {
            synchronized (cacheManagerLock)
            {
                if(instance == null)
                    instance = new CacheManager();
            }
        }
        return instance;
    }

    public  CacheService getCacheService(String providerName,Serializer serializer)
    {
        if(cacheServiceMap.get(providerName) == null)
        {
            synchronized (cacheServiceLock)
            {
                if(cacheServiceMap.get(providerName) == null)
                {
                    String cacheProviderClass = cacheProviders.get(providerName);
                    try
                    {
                        CacheService cacheService = (CacheService)this.getClass().getClassLoader()
                                .loadClass(cacheProviderClass)
                                .getConstructor(Serializer.class)
                                .newInstance(serializer);
                        cacheServiceMap.put(providerName,cacheService);
                    }catch(InvocationTargetException exception){
                        exception.getTargetException();
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        }

        return cacheServiceMap.get(providerName);
    }
}
