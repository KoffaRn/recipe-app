package org.koffa.recipefrontend.config;

import org.koffa.recipefrontend.gui.get.RecipeCard;
import org.koffa.recipefrontend.pojo.Recipe;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class RecipeCardConfig {
    @Bean
    @Scope("prototype")
    public RecipeCard recipeCard(Recipe recipe) {
        return new RecipeCard(recipe);
    }
}
