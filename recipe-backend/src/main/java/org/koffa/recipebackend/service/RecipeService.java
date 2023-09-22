package org.koffa.recipebackend.service;

import org.koffa.recipebackend.entity.Recipe;
import org.koffa.recipebackend.repository.RecipeRepository;
import org.springframework.stereotype.Service;

@Service
public class RecipeService {
    private final RecipeRepository recipeRepository;
    public RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }
    public Recipe save(Recipe recipe) {
        return recipeRepository.save(recipe);
    }
}