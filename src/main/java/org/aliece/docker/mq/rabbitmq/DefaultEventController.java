package org.aliece.docker.mq.rabbitmq;

/**
 * Created by zhangsaizhong on 15/9/9.
 */


import org.aliece.docker.Utils.CodecFactory;
import org.aliece.docker.Utils.DefaultEventTemplate;
import org.aliece.docker.Utils.EventTemplate;
import org.aliece.docker.Utils.HessionCodecFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 和rabbitmq通信的控制器，主要负责：
 * <p>1、和rabbitmq建立连接</p>
 * <p>2、声明exChange和queue以及它们的绑定关系</p>
 * <p>3、启动消息监听容器，并将不同消息的处理者绑定到对应的exchange和queue上</p>
 * <p>4、持有消息发送模版以及所有exchange、queue和绑定关系的本地缓存</p>
 * @author yangyong
 *
 */
public class DefaultEventController implements EventController {

    private CodecFactory defaultCodecFactory = new HessionCodecFactory();

    private SimpleMessageListenerContainer msgListenerContainer; // rabbitMQ msg listener container

    private MessageAdapterHandler msgAdapterHandler = new MessageAdapterHandler();

//    private MessageConverter serializerMessageConverter = new SerializerMessageConverter(); // 直接指定
    //queue cache, key is exchangeName
    private Map<String, DirectExchange> exchanges = new HashMap<String,DirectExchange>();
    //queue cache, key is queueName
    private Map<String, Queue> queues = new HashMap<String, Queue>();
    //bind relation of queue to exchange cache, value is exchangeName | queueName
    private Set<String> binded = new HashSet<String>();

    private EventTemplate eventTemplate; // 给App使用的Event发送客户端

    private AmqpAdmin amqpAdmin;

    private ConnectionFactory rabbitConnectionFactory;

    private AtomicBoolean isStarted = new AtomicBoolean(false);

    public DefaultEventController(RabbitTemplate rabbitTemplate,AmqpAdmin amqpAdmin,ConnectionFactory rabbitConnectionFactory){

        this.rabbitConnectionFactory = rabbitConnectionFactory;
        this.amqpAdmin = amqpAdmin;
        eventTemplate = new DefaultEventTemplate(rabbitTemplate,defaultCodecFactory, this);
    }
    /**
     * 注销程序
     */
    public synchronized void destroy() throws Exception {
        if (!isStarted.get()) {
            return;
        }
//        msgListenerContainer.stop();
        eventTemplate = null;
    }

    /**
     * 初始化消息监听器容器
     */
    private void initMsgListenerAdapter(){
        MessageListener listener = new MessageListenerAdapter(msgAdapterHandler);
        msgListenerContainer = new SimpleMessageListenerContainer();
        msgListenerContainer.setConnectionFactory(rabbitConnectionFactory);
        msgListenerContainer.setAcknowledgeMode(AcknowledgeMode.AUTO);
        msgListenerContainer.setMessageListener(listener);
        msgListenerContainer.setErrorHandler(new MessageErrorHandler());
        msgListenerContainer.setQueues(queues.values().toArray(new Queue[queues.size()]));
        msgListenerContainer.start();
    }

    @Override
    public void start() {
        if (isStarted.get()) {
            return;
        }
        Set<String> mapping = msgAdapterHandler.getAllBinding();
        for (String relation : mapping) {
            String[] relaArr = relation.split("\\|");
            declareBinding(relaArr[1], relaArr[0]);
        }
        initMsgListenerAdapter();
        isStarted.set(true);
    }

    @Override
    public EventTemplate getEopEventTemplate() {
        return eventTemplate;
    }

    @Override
    public EventController add(String queueName, String exchangeName,EventProcesser eventProcesser) {
        return add(queueName, exchangeName, eventProcesser, defaultCodecFactory);
    }

    public EventController add(String queueName, String exchangeName,EventProcesser eventProcesser,CodecFactory codecFactory) {
        msgAdapterHandler.add(queueName, exchangeName, eventProcesser, defaultCodecFactory);
        if(isStarted.get()){
            initMsgListenerAdapter();
        }
        return this;
    }

    @Override
    public EventController add(Map<String, String> bindings,
                               EventProcesser eventProcesser) {
        return add(bindings, eventProcesser,defaultCodecFactory);
    }

    public EventController add(Map<String, String> bindings,
                               EventProcesser eventProcesser, CodecFactory codecFactory) {
        for(Map.Entry<String, String> item: bindings.entrySet())
            msgAdapterHandler.add(item.getKey(),item.getValue(), eventProcesser,codecFactory);
        return this;
    }

    /**
     * exchange和queue是否已经绑定
     */
    public boolean beBinded(String exchangeName, String queueName) {
        return binded.contains(exchangeName+"|"+queueName);
    }

    /**
     * 声明exchange和queue以及它们的绑定关系
     */
    public synchronized void declareBinding(String exchangeName, String queueName) {
        String bindRelation = exchangeName+"|"+queueName;
        if (binded.contains(bindRelation)) return;

        boolean needBinding = false;
        DirectExchange directExchange = exchanges.get(exchangeName);
        if(directExchange == null) {
            directExchange = new DirectExchange(exchangeName, true, false, null);
            exchanges.put(exchangeName, directExchange);
            amqpAdmin.declareExchange(directExchange);//声明exchange
            needBinding = true;
        }

        Queue queue = queues.get(queueName);
        if(queue == null) {
            queue = new Queue(queueName, true, false, false);
            queues.put(queueName, queue);
            amqpAdmin.declareQueue(queue);	//声明queue
            needBinding = true;
        }

        if(needBinding) {
            Binding binding = BindingBuilder.bind(queue).to(directExchange).with(queueName);//将queue绑定到exchange
            amqpAdmin.declareBinding(binding);//声明绑定关系
            binded.add(bindRelation);
        }
    }

}
