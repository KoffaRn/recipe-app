package org.koffa.recipebackend.kafka;

import org.koffa.recipebackend.entity.ChatMessage;
import org.koffa.recipebackend.entity.Recipe;
import org.koffa.recipebackend.service.ChatMessageService;
import org.koffa.recipebackend.service.RecipeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {
    private final RecipeService recipeService;
    private final ChatMessageService chatMessageService;
    Logger logger = LoggerFactory.getLogger(KafkaProducer.class);
    public KafkaConsumer(RecipeService recipeService, ChatMessageService chatMessageService) {
        this.recipeService = recipeService;
        this.chatMessageService = chatMessageService;
    }
    @KafkaListener(topics = "${spring.kafka.recipe.topic-name}", groupId = "${spring.kafka.recipe.group-id}")
    public void consume(Recipe recipe) {
        try {
            recipeService.save(recipe);
            logger.info("Recipe saved to database: " + recipe);
        } catch (Exception e) {
            logger.error("Recived message:  but could not be saved to database " + recipe.toString() + " " + e.getMessage());
        }
    }
    @KafkaListener(topics = "${spring.kafka.chat.topic-name}", groupId = "${spring.kafka.chat.group-id}", containerFactory = "kafkaChatListenerContainerFactory")
    public void consume(ChatMessage message) {
        try {
            logger.info("Recived message: " + message);
            chatMessageService.save(message);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }
}
