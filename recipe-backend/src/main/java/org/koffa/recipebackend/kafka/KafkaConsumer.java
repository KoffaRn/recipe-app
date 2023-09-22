package org.koffa.recipebackend.kafka;

import org.koffa.recipebackend.entity.Recipe;
import org.koffa.recipebackend.service.RecipeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {
    private final RecipeService recipeService;
    @Value("${spring.kafka.topic-name}")
            private String topic;
    Logger logger = LoggerFactory.getLogger(KafkaProducer.class);
    public KafkaConsumer(RecipeService recipeService) {
        this.recipeService = recipeService;
    }
    @KafkaListener(topics = "${spring.kafka.topic-name}", groupId = "${spring.kafka.group-id}")
    public void consume(Recipe recipe) {
        try {
            recipeService.save(recipe);
            logger.info("Recipe saved to database: " + recipe);
        } catch (Exception e) {
            logger.error("Recived message:  but could not be saved to database " + recipe.toString() + " " + e.getMessage());
        }

    }
}
