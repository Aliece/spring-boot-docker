package org.aliece.docker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

/**
 * Created by zhangsaizhong on 15/9/9.
 */
@Service
public class OrderService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public void getOrder() {
        stringRedisTemplate.opsForValue().set("message", "Hello, World");
        final String message = stringRedisTemplate.opsForValue().get("message");
    }
}
