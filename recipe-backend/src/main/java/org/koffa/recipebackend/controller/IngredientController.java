package org.koffa.recipebackend.controller;


import org.koffa.recipebackend.entity.Ingredient;
import org.koffa.recipebackend.repository.IngredientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * This class is responsible for handling the chat messages.
 */
@RestController
@RequestMapping("api/v1/ingredient")
public class IngredientController {
    private final Logger logger = LoggerFactory.getLogger(IngredientController.class);
    private final IngredientRepository repository;
    public IngredientController(IngredientRepository repository) {
        this.repository = repository;
    }
    @RequestMapping("/get")
    public ResponseEntity<Iterable<Ingredient>> getAll() {
        // Get all chat messages for a recipe
        try {
            return ResponseEntity.ok().body(repository.findAll());
        }
        catch (Exception e) {
            logger.error("Error returning ingredients: " + e.getMessage());
            return ResponseEntity.internalServerError().body(null);
        }
    }
}