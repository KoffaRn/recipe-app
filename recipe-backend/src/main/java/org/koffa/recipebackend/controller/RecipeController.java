package org.koffa.recipebackend.controller;

import org.koffa.recipebackend.entity.Recipe;
import org.koffa.recipebackend.kafka.KafkaProducer;
import org.koffa.recipebackend.repository.RecipeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/recipe")
public class RecipeController {
    private final Logger logger = LoggerFactory.getLogger(RecipeController.class);
    private final KafkaProducer kafkaProducer;
    private final RecipeRepository repository;
    public RecipeController(KafkaProducer kafkaProducer, RecipeRepository repository) {
        this.kafkaProducer = kafkaProducer;
        this.repository = repository;
    }
    @GetMapping("/get")
    public ResponseEntity<List<Recipe>> getAll() {
        try {
            return ResponseEntity.ok().body(repository.findAll());
        }
        catch (Exception e) {
            logger.error("Error returning recipes: " + e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/get/tag")
    public ResponseEntity<List<String>> getAllTags() {
        try {
            return new ResponseEntity<>(repository.findAllUniqueTags(), HttpStatus.OK);
        }
        catch (Exception e) {
            logger.error("Error returning tags: " + e.getMessage());
            return ResponseEntity.internalServerError().body(null);
        }
    }
    @GetMapping("/get/tag/{tag}")
    public ResponseEntity<List<Recipe>> getAllByTag(@PathVariable String tag) {
        try {
            return ResponseEntity.ok().body(repository.findAllByTag(tag));
        }
        catch (Exception e) {
            logger.error("Error returning recipes: " + e.getMessage());
            return ResponseEntity.internalServerError().body(null);
        }
    }
    @GetMapping("/get/{id}")
    public ResponseEntity<Recipe> getOne(@PathVariable long id) {
        Optional<Recipe> recipe = repository.findById(id);
        if(recipe.isPresent()) {
            return new ResponseEntity<>(recipe.get(), HttpStatus.OK);
        } else {
            logger.error("Error getting recipe, no recipe with " + id);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/publish")
    public ResponseEntity<String> publish(@RequestBody Recipe message) {
        try {
            kafkaProducer.sendMessage(message);
            return ResponseEntity.ok().body("Message sent to kafka: " + message);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error sending message: " + e.getMessage());
        }
    }
}