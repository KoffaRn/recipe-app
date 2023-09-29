package org.koffa.recipefrontend.gui.get;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.koffa.recipefrontend.api.ApiHandler;
import org.koffa.recipefrontend.pojo.Recipe;
import org.springframework.stereotype.Component;

public class RecipeCard extends VBox {
    public RecipeCard(Recipe recipe, String url) {
        SplitPane card = new SplitPane();
        VBox recipeBox = new VBox();
        Button getRecipeButton = new Button("View recipe");
        Label recipeName = new Label(recipe.getName());
        Label recipeDescription = new Label(recipe.getDescription());
        recipeBox.getChildren().addAll(recipeName, recipeDescription);
        card.getItems().addAll(recipeBox, getRecipeButton);
        this.getChildren().addAll(card);
        getRecipeButton.setOnAction(event -> {
            FullRecipe fullRecipe = new FullRecipe(new ApiHandler());
            fullRecipe.setRecipe(recipe);
            fullRecipe.setUrl(url);
            try {
                fullRecipe.start(new Stage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
