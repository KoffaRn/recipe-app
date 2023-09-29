package org.koffa.recipebackend.kafka;

import org.koffa.recipebackend.entity.ChatMessage;
import org.koffa.recipebackend.entity.Recipe;
import org.koffa.recipebackend.service.ChatMessageService;
import org.koffa.recipebackend.service.RecipeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

/**
 * This class is responsible for consuming messages from Kafka.
 */

@Service
public class KafkaConsumer {
    private final RecipeService recipeService;
    private final ChatMessageService chatMessageService;
    private final Logger logger = LoggerFactory.getLogger(KafkaProducer.class);
    public KafkaConsumer(RecipeService recipeService, ChatMessageService chatMessageService) {
        this.recipeService = recipeService;
        this.chatMessageService = chatMessageService;
    }

    @KafkaListener(topics = "${spring.kafka.recipe.topic-name}", groupId = "${spring.kafka.recipe.group-id}")
    public void consume(Recipe recipe) {
        // Save the recipe to the database
        try {
            recipeService.save(recipe);
            logger.info("Recipe saved to database: " + recipe);
        } catch (Exception e) {
            logger.error("Received message:  but could not be saved to database " + recipe.toString() + " " + e.getMessage());
        }
    }
    @KafkaListener(topics = "${spring.kafka.chat.topic-name}", groupId = "${spring.kafka.chat.group-id}", containerFactory = "kafkaChatListenerContainerFactory") // This is the container factory for the chat messages
    public void consume(ChatMessage message) {
        // Save the chat message to the database
        try {
            logger.info("Received message: " + message);
            chatMessageService.save(message);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }
}
