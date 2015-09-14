package org.aliece.docker.mq.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ErrorHandler;

/**
 * Created by zhangsaizhong on 15/9/9.
 */
public class MessageErrorHandler implements ErrorHandler {

    private static final Logger logger = LoggerFactory.getLogger(MessageErrorHandler.class);

    @Override
    public void handleError(Throwable t) {
        logger.error("RabbitMQ happen a error:" + t.getMessage(), t);
    }

}
