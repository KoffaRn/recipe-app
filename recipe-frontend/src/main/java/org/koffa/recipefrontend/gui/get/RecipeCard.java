package org.koffa.recipefrontend.gui.get;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.koffa.recipefrontend.pojo.Recipe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

public class RecipeCard extends VBox {
    private final Logger logger = LoggerFactory.getLogger(RecipeCard.class);
    @Autowired
    BeanFactory beanFactory;
    private Recipe recipe;
    @Value(value = "${websocket.url}")
    private String url;
    private FullRecipe fullRecipe;
    public RecipeCard(Recipe recipe) {
        SplitPane card = new SplitPane();
        VBox recipeBox = new VBox();
        Button getRecipeButton = new Button("View recipe");
        Label recipeName = new Label(recipe.getName());
        Label recipeDescription = new Label(recipe.getDescription());
        recipeBox.getChildren().addAll(recipeName, recipeDescription);
        card.getItems().addAll(recipeBox, getRecipeButton);
        this.getChildren().addAll(card);
        getRecipeButton.setOnAction(event -> {
            fullRecipe = beanFactory.getBean(FullRecipe.class, recipe);
            try {
                fullRecipe.start(new Stage());
            } catch (Exception e) {
                logger.error("Error starting full recipe > " + e.getMessage());
            }
        });
    }
}
