package org.aliece.docker.redis;

import org.springframework.data.redis.core.StringRedisTemplate;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by zhangsaizhong on 15/9/10.
 */
public class RedisHashClient {

    private StringRedisTemplate redisTemplate;

    public RedisHashClient(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public  void updateOrAdd(String key, Serializable field, Serializable value) {
        redisTemplate.opsForHash().put(key, field, value);
    }

    public void updateOrAddMulti(String key, Map<Serializable, Serializable> entries) {
        redisTemplate.opsForHash().putAll(key, entries);
    }

    /**
     * if field exists, do nothing
     * if key does not exist, create a new hash table, then add
     * @param key
     * @param field
     * @param value
     */
    public  Boolean addIfAbsent(String key, Serializable field, Serializable value) {
        return redisTemplate.opsForHash().putIfAbsent(key, field, value);
    }

    public Double incrementOrAdd(String key, Serializable field, double delta) {
        return redisTemplate.opsForHash().increment(key, field, delta);
    }

    public Long incrementOrAdd(String key, Serializable field, long delta) {
        return redisTemplate.opsForHash().increment(key, field, delta);
    }


    public Object get(String key, Serializable field) {
        return redisTemplate.opsForHash().get(key, field);
    }

    public List<Object> getMulti(String key, Collection<Object> fields) {
        return redisTemplate.opsForHash().multiGet(key, fields);
    }

    public Map<Object, Object> entries(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    public List<Object> values(String key) {
        return redisTemplate.opsForHash().values(key);
    }

    public Boolean hasField(String key, Serializable field) {
        return redisTemplate.opsForHash().hasKey(key, field);
    }

    public Set<Object> fields(String key) {
        return redisTemplate.opsForHash().keys(key);
    }

    public long size(String key) {
        return redisTemplate.opsForHash().size(key);
    }

    public void delete(String key, Serializable... fields) {
        redisTemplate.opsForHash().delete(key, fields);
    }

    public void scan(String key) {
        //TODO
        //redisTemplate.opsForHash().scan(key, ScanOptions.scanOptions().build());
    }
}
