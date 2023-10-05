package org.koffa.recipefrontend.gui.get;

import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import org.koffa.recipefrontend.api.ApiHandler;
import org.koffa.recipefrontend.api.RecipeGetter;
import org.koffa.recipefrontend.pojo.Recipe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
@Component
public class GetBox extends VBox {
    Logger logger = LoggerFactory.getLogger(GetBox.class);
    @Autowired
    BeanFactory beanFactory;
    @Autowired
    ApiHandler apiHandler;
    private RecipeGetter recipeGetter;
    private SplitPane recipeCards;
    private ButtonBar tagsBar;

    public GetBox()  {
        if(apiHandler == null) {
            showApiError();

        } else {
            getContentFromApi();
        }
    }

    private void showApiError() {
        TextFlow errorMessage = new TextFlow();
        Text errorText = new Text("Could not create API instance, make sure URL is configured and backend up and running.");
        errorText.setFill(Color.RED);
        errorMessage.getChildren().add(errorText);
        logger.error("ApiHandler is null");
        this.getChildren().add(errorMessage);
    }

    private void getContentFromApi() {
        Button allRecipesButton = new Button("All recipes");

        this.tagsBar = new ButtonBar();
        tagsBar.getButtons().add(allRecipesButton);
        this.recipeGetter = apiHandler;
        try {
            populateTags(apiHandler.getAllTags());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

        this.recipeCards = new SplitPane();
        recipeCards.setOrientation(javafx.geometry.Orientation.VERTICAL);

        this.getChildren().addAll(tagsBar, recipeCards);

        allRecipesButton.setOnAction(event -> {
            try {
                tagsBar.getButtons().clear();
                recipeCards.getItems().clear();
                tagsBar.getButtons().add(allRecipesButton);
                populateTags(apiHandler.getAllTags());
                populateList(apiHandler.getAllRecipes(), recipeCards);
            } catch (IOException e) {
                logger.error(e.getMessage());
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
            recipeCards.getItems().add(beanFactory.getBean(RecipeCard.class, recipe));
        }
    }
}
