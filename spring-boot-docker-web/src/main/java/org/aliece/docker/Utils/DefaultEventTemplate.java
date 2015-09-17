package org.aliece.docker.Utils;

import org.aliece.docker.mq.rabbitmq.DefaultEventController;
import org.aliece.docker.mq.rabbitmq.message.EventMessage;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.io.IOException;

/**
 * Created by zhangsaizhong on 15/9/9.
 */
public class DefaultEventTemplate implements EventTemplate {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultEventTemplate.class);

    private AmqpTemplate eventAmqpTemplate;

    private CodecFactory defaultCodecFactory;

	private DefaultEventController eec;

	public DefaultEventTemplate(AmqpTemplate eopAmqpTemplate,
			CodecFactory defaultCodecFactory, DefaultEventController eec) {
		this.eventAmqpTemplate = eopAmqpTemplate;
		this.defaultCodecFactory = defaultCodecFactory;
		this.eec = eec;
	}

    @Override
    public void send(String queueName, String exchangeName, Object eventContent)
            throws Exception {
        this.send(queueName, exchangeName, eventContent, defaultCodecFactory);
    }

    @Override
    public void send(String queueName, String exchangeName, Object eventContent,
                     CodecFactory codecFactory) throws Exception {
        if (StringUtils.isEmpty(queueName) || StringUtils.isEmpty(exchangeName)) {
            throw new Exception("queueName exchangeName can not be empty.");
        }

		if (!eec.beBinded(exchangeName, queueName))
			eec.declareBinding(exchangeName, queueName);

        byte[] eventContentBytes = null;
        if (codecFactory == null) {
            if (eventContent == null) {
                LOGGER.warn("Find eventContent is null,are you sure...");
            } else {
                throw new Exception(
                        "codecFactory must not be null ,unless eventContent is null");
            }
        } else {
            try {
                eventContentBytes = codecFactory.serialize(eventContent);
            } catch (IOException e) {
                throw new Exception(e);
            }
        }

        // 构造成Message
        EventMessage msg = new EventMessage(queueName, exchangeName,
                eventContentBytes);
        try {
            eventAmqpTemplate.convertAndSend(exchangeName, queueName, msg);
        } catch (AmqpException e) {
            LOGGER.error("send event fail. Event Message : [" + eventContent + "]", e);
            throw new Exception("send event fail", e);
        }
    }
}
