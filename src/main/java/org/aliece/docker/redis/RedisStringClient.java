package org.aliece.docker.redis;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by zhangsaizhong on 15/9/10.
 */
public class RedisStringClient {

    public final  int DEFAULT_EXPIRE_TIME = 30; //1*60

    private StringRedisTemplate redisTemplate;

    public RedisStringClient(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public  void updateOrAdd(final String key, final String value) {
        updateOrAdd(key, value, DEFAULT_EXPIRE_TIME, TimeUnit.SECONDS);
    }

    public  void updateOrAdd(final String key, final String value, final long expireTime, final TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, value, expireTime, timeUnit);
    }

    public  void updateOrAddMulti(Map<String, String> entities) {
        redisTemplate.opsForValue().multiSet(entities);
    }

    public  Boolean addIfAbsent(final String key, final String value) {
        return redisTemplate.opsForValue().setIfAbsent(key, value);
    }

    public  Boolean addIfAbsentMulti(Map<String, String> entities) {
        return redisTemplate.opsForValue().multiSetIfAbsent(entities);
    }

    public  void replaceOrAdd(final String key, final String value, final long offset) {
        redisTemplate.opsForValue().set(key, value, offset);
    }

//    public  Boolean setBitOrAdd(final String key, long offset, final boolean value) {
//        return redisTemplate.opsForValue().setBit(key, offset, value);
//    }

    public  Integer appendOrAdd(final String key, final String value) {
        return redisTemplate.opsForValue().append(key, value);
    }

    public  Double incrementOrAdd(final String key, double delta) {
        return redisTemplate.opsForValue().increment(key, delta);
    }

    public  Long incrementOrAdd(final String key, long delta) {
        return redisTemplate.opsForValue().increment(key, delta);
    }

    public  String read(final String key, final long start, final long end) {
        return redisTemplate.opsForValue().get(key, start, end);
    }

    public  String read(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public  String readAndSet(final String key, final String value) {
        return redisTemplate.opsForValue().getAndSet(key, value);
    }

//    public  Boolean readBit(final String key, long offset) {
//        return redisTemplate.opsForValue().getBit(key, offset);
//    }

    public List<String> readMulti(Collection<String> keys) {
        return redisTemplate.opsForValue().multiGet(keys);
    }
}
