package org.aliece.docker.mq.rabbitmq.message;

import lombok.Data;

/**
 * Created by zhangsaizhong on 15/9/8.
 */
@Data
public class ContentMessage {
    private String content;
    public ContentMessage(String content) {
        this.content = content;
    }
}
