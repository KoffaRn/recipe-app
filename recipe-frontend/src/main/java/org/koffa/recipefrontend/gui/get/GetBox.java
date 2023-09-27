package org.koffa.recipefrontend.gui.get;

import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.VBox;
import org.koffa.recipefrontend.api.RecipeGetter;
import org.koffa.recipefrontend.pojo.Recipe;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
@Component
public class GetBox extends VBox {
    @Value(value = "${websocket.url}")
    private String url;
    RecipeGetter recipeGetter;
    SplitPane recipeCards;
    ButtonBar tagsBar;

    public GetBox(RecipeGetter recipeGetter) throws IOException {
        Button allRecipesButton = new Button("All recipes");

        this.tagsBar = new ButtonBar();
        tagsBar.getButtons().add(allRecipesButton);
        this.recipeGetter = recipeGetter;
        populateTags(recipeGetter.getAllTags());

        this.recipeCards = new SplitPane();
        recipeCards.setOrientation(javafx.geometry.Orientation.VERTICAL);

        this.getChildren().addAll(tagsBar, recipeCards);

        allRecipesButton.setOnAction(event -> {
            try {
                tagsBar.getButtons().clear();
                recipeCards.getItems().clear();
                tagsBar.getButtons().add(allRecipesButton);
                populateTags(recipeGetter.getAllTags());
                populateList(recipeGetter.getAllRecipes(), recipeCards);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void populateTags(ArrayList<String> tags) {
        for(String tag : tags) {
            Button tagButton = getButton(recipeGetter, tag);
            tagsBar.getButtons().add(tagButton);
        }
    }

    private Button getButton(RecipeGetter recipeGetter, String tag) {
        Button tagButton = new Button(tag);
        tagButton.setOnAction(event -> {
            try {
                recipeCards.getItems().clear();
                populateList(recipeGetter.getRecipesByTag(tag), recipeCards);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        return tagButton;
    }

    private void populateList(List<Recipe> recipes, SplitPane recipeCards) {
        for(Recipe recipe : recipes) {
            recipeCards.getItems().add(new RecipeCard(recipe, url));
        }
    }
}
