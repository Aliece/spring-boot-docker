package org.aliece.docker.redis;

import org.springframework.data.redis.core.StringRedisTemplate;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by zhangsaizhong on 15/9/10.
 */
public class RedisListClient {
    
    private StringRedisTemplate redisTemplate;

    public RedisListClient(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
    /**
     * 压栈
     * @param key
     * @param value
     * @return
     */
    public Long push(String key, String value) {
        return redisTemplate.opsForList().leftPush(key, value);
    }

    /**
     * 出栈
     * @param key
     * @return
     */
    public  Serializable pop(String key) {
        return redisTemplate.opsForList().leftPop(key);
    }

    /**
     *
     * @param key
     * @param timeout
     * @param timeUnit
     * @return
     */
    public  Serializable popBlock(String key, long timeout, TimeUnit timeUnit) {
        return redisTemplate.opsForList().leftPop(key, timeout, timeUnit);
    }

    /**
     * 入队
     * @param key
     * @param value
     * @return
     */
    public  Long in(String key, String value) {
        return redisTemplate.opsForList().rightPush(key, value);
    }

    /**
     * 出队
     * @param key
     * @return
     */
    public  Serializable out(String key) {
        return redisTemplate.opsForList().leftPop(key);
    }

    /**
     * 栈/队列长
     * @param key
     * @return
     */
    public  Long length(String key) {
        return redisTemplate.opsForList().size(key);
    }

    /**
     * 范围检索
     * @param key
     * @param start
     * @param end
     * @return
     */
    public List<String> range(String key, long start, long end) {
        return redisTemplate.opsForList().range(key, start, end);
    }

    /**
     * 移除
     * @param key
     * @param i
     * @param value
     */
    public  void remove(String key, long i, Serializable value) {
        redisTemplate.opsForList().remove(key, i, value);
    }

    /**
     * 检索
     * @param key
     * @param index
     * @return
     */
    public  Serializable index(String key, long index) {
        return redisTemplate.opsForList().index(key, index);
    }

    /**
     * 置值
     * @param key
     * @param index
     * @param value
     */
    public  void set(String key, long index, String value) {
        redisTemplate.opsForList().set(key, index, value);
    }

    /**
     * 裁剪
     * @param key
     * @param start
     * @param end
     */
    public  void trim(String key, long start, int end) {
        redisTemplate.opsForList().trim(key, start, end);
    }
}
