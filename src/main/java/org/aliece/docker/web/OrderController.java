package org.aliece.docker.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/**
 * Created by zhangsaizhong on 15/9/8.
 */

@RestController
@RequestMapping("/api/v/o")
public class OrderController {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @RequestMapping(value = "order", method = RequestMethod.GET)
    public String order(){
        stringRedisTemplate.opsForValue().set("message", "Hello, World");
        final String message = stringRedisTemplate.opsForValue().get("message");
        return message;

    }
}
