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
@Service
public class OrderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.queueName}")
    String queueName;

    @Value("${rabbitmq.exchangeName}")
    String exchangeName;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private ProductStoreMapper productStoreMapper;

    @Autowired
    private OrderMapper orderMapper;

    private CodecFactory defaultCodecFactory = new HessionCodecFactory();

    @Autowired
    DefaultEventController eventController;

    public boolean createOrder(String productId, String userId) {
        EventTemplate eventTemplate = eventController.getEopEventTemplate();
        try {
            Long store=null;
            ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();

            String value = valueOperations.get(productId);
            if (StringUtils.isEmpty(value)) {
                ProductStore productStore = productStoreMapper.selectByProductID(productId);
                store = productStore.getStore();
                valueOperations.set(productId+"-store",String.valueOf(store));
            } else {
                store = Long.valueOf(valueOperations.get(productId+"-store"));
            }

            if (StringUtils.isNotEmpty(value) && Long.valueOf(value) > store) {
                return false;
            } else {
                valueOperations.increment(productId, 1);
                Order order = new Order();
                order.setAccountID(Long.valueOf(userId));
                order.setProductID(Long.valueOf(productId));
                order.setCreateTime(new Date());
                order.setLastModifyTime(new Date());
                eventTemplate.send(queueName, exchangeName, order);
                LOGGER.info("Product Message sent to RabbitMQ: " + productId);
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @RabbitListener(queues = "${rabbitmq.queueName}")
    public void receiveMessage(EventMessage msg) throws Exception {
        LOGGER.info("Received incoming object From RabbitMQ " + msg);
        try {
            Order order = (Order) defaultCodecFactory.deSerialize(msg.getEventData());
            ProductStore productStore = productStoreMapper.selectByProductID(String.valueOf(order.getProductID()));
            if (productStore.getStore() > 0) {
                orderMapper.insert(order);
                productStore.setStore(productStore.getStore() - 1);
                productStoreMapper.update(productStore);
            }
        } catch (IOException e) {
            throw new Exception(e);
        }
    }

}
