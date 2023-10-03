package org.koffa.recipefrontend.config;

import org.koffa.recipefrontend.api.ApiHandler;
import org.koffa.recipefrontend.gui.get.FullRecipe;
import org.koffa.recipefrontend.pojo.Recipe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class FullRecipeConfig {
    @Autowired
    private ApiHandler apiHandler;
    @Value(value = "${websocket.url}")
    private String url;
    @Bean
    @Scope("prototype")
    public FullRecipe fullRecipe(Recipe recipe) {
        return new FullRecipe(apiHandler, recipe, url);
    }
}
