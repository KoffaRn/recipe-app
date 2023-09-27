package org.koffa.recipefrontend.kafka;

import org.koffa.recipefrontend.gui.send.LoggerBox;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {
    LoggerBox loggerBox;

    public KafkaConsumer(LoggerBox loggerBox) {
        this.loggerBox = loggerBox;
    }
    @KafkaListener(topics = "${spring.kafka.recipe-topic}", groupId = "${spring.kafka.group-id}")
    public void listen(String message) {
        loggerBox.info("Received Message >> " + message);
    }
}