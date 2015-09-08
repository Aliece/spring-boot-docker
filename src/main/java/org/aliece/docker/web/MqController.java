package org.aliece.docker.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by zhangsaizhong on 15/9/8.
 */
@RestController
public class MqController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MqController.class);

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.queueName}")
    String queueName;

    @RequestMapping(value = "hello", method = RequestMethod.GET)
    public String sayHello(@RequestParam("name") String name){
        rabbitTemplate.convertAndSend(queueName, name);
        return "Message sent to RabbitMQ: Hello from "+name;

    }

    @RabbitListener(queues = "${rabbitmq.queueName}")
    public void receiveMessage(String message) {
        LOGGER.info("Received incoming object at " + message);
    }

}
