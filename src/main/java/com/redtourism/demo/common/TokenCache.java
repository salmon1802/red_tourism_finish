package com.redtourism.demo.common;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * @date 2020-12-1 - 19:56
 * Created by Salmon
 */
public class TokenCache {
    private static Logger logger = LoggerFactory.getLogger(TokenCache.class);

    public static final String TOKEN_PREFIX = "token_";

    //LRU算法,即当最大值超过一万时会使用最小使用算法进行清除
    private static LoadingCache<String,String> localCache = CacheBuilder.newBuilder().initialCapacity(1000).maximumSize(10000).expireAfterAccess(12, TimeUnit.HOURS)
            .build(new CacheLoader<String,String>(){
                //默认的数据加载实现，当调用get取值的时候，如果key没有对应的值，就调用这个方法进行加载。
                public String load(String s) throws Exception{
                    return "null";
                }
            });

    public static void setKey(String key,String value){
        localCache.put(key, value);
    }

    public static String getKey(String key){
        String value = null;
        try {
            value = localCache.get(key);
            if("null".equals(value)){
                return null;
            }
            return value;
        } catch (Exception e) {
            logger.error("localCache get error",e);
        }
        return null;

    }
}
