package org.aliece.docker;

import org.aliece.docker.redis.RedisStringClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by zhangsaizhong on 15/9/10.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class RedisTest {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void testGet() throws Exception{
        ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();
        System.out.println(valueOperations.increment("test1", 0L));
    }

    @Test
    public void testIncr() throws Exception{
        for (int i = 0; i <200;i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();
                    if(!stringRedisTemplate.hasKey("test1")) {
                        valueOperations.increment("test1", 1L);
                    } {
                        valueOperations.increment("test1", 1L);
                    }
//                    if (valueOperations.increment("test1", -1L) < 0) {
//                        System.out.println(11111111);
//                    } else {
//
//                    }
                }
            }).start();
        }

    }

}
