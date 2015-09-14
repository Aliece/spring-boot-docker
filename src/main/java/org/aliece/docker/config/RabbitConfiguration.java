package org.aliece.docker.config;

import org.aliece.docker.mq.rabbitmq.DefaultEventController;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.amqp.support.converter.SerializerMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by zhangsaizhong on 15/9/8.
 */
@Configuration
public class RabbitConfiguration {

    @Value("${rabbitmq.queueName}")
    String queueName;

    @Value("${rabbitmq.exchangeName}")
    String exchangeName;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    AmqpAdmin amqpAdmin;

    @Autowired
    ConnectionFactory rabbitConnectionFactory;

    @Bean
    MessageConverter messageConverter(){
        return new SerializerMessageConverter();
    }

    @Bean
    DefaultEventController eventController(RabbitTemplate rabbitTemplate, AmqpAdmin amqpAdmin,ConnectionFactory rabbitConnectionFactory) {
        return new DefaultEventController(rabbitTemplate,amqpAdmin,rabbitConnectionFactory);
    }

    @Bean
    Queue queue() {
        return new Queue(queueName, false);
    }

    @Bean
    DirectExchange exchange() {
        return new DirectExchange(exchangeName);
    }

    @Bean
    Binding binding(Queue queue, DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(queueName);
    }

}
