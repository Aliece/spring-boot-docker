package org.aliece.docker.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
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

    @Bean
    Queue queue() {
        return new Queue(queueName, false);
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange(exchangeName);
    }

    @Bean
    Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(queueName);
    }

//    @Bean
//    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory, MessageListenerAdapter listenerAdapter) {
//        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
//        container.setConnectionFactory(connectionFactory);
//        container.setQueueNames(queueName);
//        container.setMessageListener(listenerAdapter);
//        return container;
//    }

//    @Bean
//    MqReceiver mqReceiver() {
//        return new MqReceiver();
//    }

//    @Bean
//    MessageListenerAdapter listenerAdapter(MqReceiver mqReceiver) {
//        return new MessageListenerAdapter(mqReceiver, "receiveMessage");
//    }

}
