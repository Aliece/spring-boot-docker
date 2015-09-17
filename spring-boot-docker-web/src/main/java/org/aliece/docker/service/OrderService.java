package org.aliece.docker.service;

import org.aliece.docker.Utils.CodecFactory;
import org.aliece.docker.Utils.EventTemplate;
import org.aliece.docker.Utils.HessionCodecFactory;
import org.aliece.docker.domain.Order;
import org.aliece.docker.domain.ProductStore;
import org.aliece.docker.mq.rabbitmq.DefaultEventController;
import org.aliece.docker.mq.rabbitmq.message.EventMessage;
import org.aliece.docker.repository.mapper.OrderMapper;
import org.aliece.docker.repository.mapper.ProductStoreMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;

/**
 * Created by zhangsaizhong on 15/9/9.
 */
public interface OrderService {

    public boolean createOrder(String productId, String userId);

    public void receiveMessage(EventMessage msg) throws Exception ;

}
