package org.aliece.docker.mq.rabbitmq;

import lombok.Data;

/**
 * Created by zhangsaizhong on 15/9/8.
 */
@Data
public class HelloMessage {
    private String name;

    public HelloMessage(String name) {
        this.name = name;
    }
}
