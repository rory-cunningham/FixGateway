package com.fix.gateway;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import quickfix.Message;

/**
 * @author Rory Cunningham
 */

@Component
public class KafkaProducer {

    private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(KafkaProducer.class);

    @Autowired
    private KafkaTemplate<Object, Message> kafkaTemplate;

    public void send(String topic, Message payload) {
        LOGGER.info("sending payload='{}' to topic='{}'", payload, topic);
        kafkaTemplate.send(topic, payload);
    }


}
