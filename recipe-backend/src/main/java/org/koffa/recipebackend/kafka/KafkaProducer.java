package org.koffa.recipebackend.kafka;

import org.koffa.recipebackend.entity.ChatMessage;
import org.koffa.recipebackend.entity.Recipe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

/**
 * This class is responsible for producing messages to Kafka.
 */
@Service
public class KafkaProducer {
    private final Logger logger = LoggerFactory.getLogger(KafkaProducer.class);
    @Autowired
    private KafkaTemplate<String, Recipe> kafkaTemplate;
    @Autowired
    private KafkaTemplate<String, ChatMessage> kafkaTemplateChat;
    @Value(value = "${spring.kafka.recipe.topic-name}")
    private String topicName;
    @Value(value = "${spring.kafka.chat.topic-name}")
    private String topicNameChat;
    public void sendMessage(Recipe message) {
        // Send a message to the Kafka topic
        CompletableFuture<SendResult<String, Recipe>> future = kafkaTemplate.send(topicName, message);
        // Log the result of the send
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
    // Send a message to the Kafka topic
    public void sendMessage(ChatMessage chatMessage) {
        CompletableFuture<SendResult<String, ChatMessage>> future = kafkaTemplateChat.send(topicNameChat, chatMessage);
        // Log the result of the send
        future.whenComplete((result, ex) -> {
            if (ex == null) {
                logger.info("Sent message=[" + chatMessage +
                        "] with offset=[" + result.getRecordMetadata().offset() + "]");
            } else {
                logger.error("Unable to send message=[" +
                        chatMessage + "] due to : " + ex.getMessage());
            }
        });
    }
}