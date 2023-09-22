package org.koffa.recipebackend.kafka;

import org.koffa.recipebackend.entity.Recipe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class KafkaProducer {
    private final Logger logger = LoggerFactory.getLogger(KafkaProducer.class);
    @Autowired
    private KafkaTemplate<String, Recipe> kafkaTemplate;
    @Value(value = "${spring.kafka.topic-name}")
    private String topicName;
    public void sendMessage(Recipe message) {
        CompletableFuture<SendResult<String, Recipe>> future = kafkaTemplate.send(topicName, message);
        future.whenComplete((result, ex) -> {
            if (ex == null) {
                logger.info("Sent message=[" + message +
                        "] with offset=[" + result.getRecordMetadata().offset() + "]");
            } else {
                logger.error("Unable to send message=[" +
                        message + "] due to : " + ex.getMessage());
            }
        });
    }
}