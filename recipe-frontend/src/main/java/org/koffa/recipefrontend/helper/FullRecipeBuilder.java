package org.koffa.recipefrontend.helper;

import lombok.Getter;
import org.koffa.recipefrontend.ChatWebSocketClient;
import org.koffa.recipefrontend.gui.get.FullRecipe;
import org.koffa.recipefrontend.pojo.Recipe;

public class FullRecipeBuilder {
    @Getter
    private Recipe recipe;
    @Getter
    private String url;
    public FullRecipeBuilder(Recipe recipe, String url) {
        this.recipe = recipe;
        this.url = url;
    }
    public FullRecipe build() {
        return new FullRecipe(this);
    }
}
