package org.aliece.docker.web;

import org.aliece.docker.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PathVariable;
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
    OrderService orderService;

    @RequestMapping(value = "order/{userId}/{ProductId}", method = RequestMethod.GET)
    public String order(@PathVariable("userId") String userId,@PathVariable("ProductId") String ProductId){
        if (orderService.createOrder(ProductId,userId)) {
            return "success";
        }
        return "failure";
    }
}
