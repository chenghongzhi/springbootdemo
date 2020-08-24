package com.example.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Component
public class SessionManage {

    @Autowired
    private StringRedisTemplate redisTemplate;
    /***
     * 产生一个uuid的token
     * @return
     */
    public static String generateToken(){
        return UUID.randomUUID().toString().replaceAll("-","").toLowerCase();
    }

    /**
     * 通过key查询redis
     * @param key
     * @return
     */
    public String redisGetValue(String key){
       return redisTemplate.opsForValue().get(key);
    }

    /**
     * 插入值到redis
     * @param key
     * @param value
     * @param time 设置有效时间
     * @param timeUnit TimeUnit枚举类型
     */
    public void redisSetValue(String key,String value,long time,TimeUnit timeUnit){
        redisTemplate.opsForValue().set(key, value, time, timeUnit);
    }

    /**
     * 通过key删除redis的值
     * @param key
     */
    public void redisDelete(String key){
        redisTemplate.delete(key);
    }

    public boolean redisHasKey(String key){
        return redisTemplate.hasKey(key);
    }

    /***
     * 给指定的key设定有效时间
     * @param key
     * @param time
     * @param timeUnit
     * @return
     */
    public boolean redisSetExpire(String key,long time,TimeUnit timeUnit){
        boolean result = redisTemplate.expire(key,time,timeUnit);
        return result;
    }

    /***
     * 根据key获取过期时间
     * @param key
     * @return
     */
    public Long redisGetExpire(String key){
        return redisTemplate.getExpire(key);
    }
}
