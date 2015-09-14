package org.aliece.docker.mq.rabbitmq;

/**
 * Created by zhangsaizhong on 15/9/9.
 */
public interface EventProcesser {

    public void process(Object e);
}
