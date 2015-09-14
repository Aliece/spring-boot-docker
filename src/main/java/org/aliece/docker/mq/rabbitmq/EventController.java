package org.aliece.docker.mq.rabbitmq;

import org.aliece.docker.Utils.EventTemplate;

import java.util.Map;

/**
 * Created by zhangsaizhong on 15/9/9.
 */
public interface EventController {
    /**
     * 控制器启动方法
     */
    void start();

    /**
     * 获取发送模版
     */
    EventTemplate getEopEventTemplate();

    /**
     * 绑定消费程序到对应的exchange和queue
     */
    EventController add(String queueName, String exchangeName, EventProcesser eventProcesser);

    /*in map, the key is queue name, but value is exchange name*/
    EventController add(Map<String,String> bindings, EventProcesser eventProcesser);
}
