package org.koffa.recipefrontend.gui;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import lombok.Getter;
import org.apache.kafka.clients.KafkaClient;
import org.koffa.recipefrontend.api.RecipeSender;
import org.koffa.recipefrontend.pojo.Ingredient;
import org.koffa.recipefrontend.pojo.Recipe;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
@Component
public class SendBox extends VBox {
    @Getter
    private final LoggerBox loggerBox;
    private final RecipeBox recipeBox;
    private final TagBox tagBox;
    private final IngredientBox ingredientBox;
    public SendBox(RecipeSender recipeSender, LoggerBox loggerBox) {
        this.loggerBox = loggerBox;
        this.recipeBox = new RecipeBox();
        this.ingredientBox = new IngredientBox();
        this.tagBox = new TagBox();
        ScrollPane scrollPane = new ScrollPane();
        VBox vBox = new VBox();
        Button sendButton = new Button("Send");
        vBox.getChildren().addAll(
                recipeBox,
                ingredientBox,
                tagBox,
                sendButton,
                loggerBox
        );
        scrollPane.setContent(vBox);
        this.getChildren().add(scrollPane);
        if(recipeSender == null) loggerBox.error("Could not create API instance, make sure URL is configured and backend up and running.", new RuntimeException("API ERROR"));
    }

    private Recipe getRecipe() {
        Recipe recipe = new Recipe();
        recipe.setName(recipeBox.getRecipeName());
        recipe.setDescription(recipeBox.getRecipeDescription());
        recipe.setSteps(recipeBox.getSteps());
        recipe.setTags(tagBox.getTags());
        ArrayList<Ingredient> ingredients = new ArrayList<>();
        for(Node node : ingredientBox.getIngredients())
            ingredients.add(((IngredientPane) node).getIngredient());
        recipe.setIngredients(ingredients);
        recipe.setTags(new ArrayList<>(tagBox.getTags()));
        return recipe;
    }

    public void exit() {
        System.out.println("Closing kafka client");
    }
}