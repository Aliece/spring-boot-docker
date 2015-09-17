package org.aliece.docker.redis;

import org.springframework.data.redis.core.StringRedisTemplate;

import java.io.Serializable;
import java.util.Collection;

/**
 * Created by zhangsaizhong on 15/9/10.
 */
public class RedisCommonClient {

    private StringRedisTemplate redisTemplate;

    public RedisCommonClient(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void delete(String key) {
        redisTemplate.delete(key);
    }

    public void deleteMulti(Collection keys) {
        redisTemplate.delete(keys);
    }
}
