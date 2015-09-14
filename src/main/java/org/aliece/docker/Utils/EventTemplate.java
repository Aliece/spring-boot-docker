package org.aliece.docker.Utils;

/**
 * Created by zhangsaizhong on 15/9/9.
 */
public interface EventTemplate {

    void send(String queueName,String exchangeName,Object eventContent) throws Exception;

    void send(String queueName,String exchangeName,Object eventContent,CodecFactory codecFactory) throws Exception;
}
