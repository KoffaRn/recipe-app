package org.koffa.recipefrontend.gui.get;

import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import org.koffa.recipefrontend.api.ApiHandler;
import org.koffa.recipefrontend.pojo.Recipe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
@Component
public class GetBox extends VBox {
    private final Logger logger = LoggerFactory.getLogger(GetBox.class);
    private final BeanFactory beanFactory;
    private final ApiHandler apiHandler;
    private SplitPane recipeCards;
    private ButtonBar tagsBar;

    public GetBox(ApiHandler apiHandler, BeanFactory beanFactory)  {
        this.beanFactory = beanFactory;
        this.apiHandler = apiHandler;
        try {
            getContentFromApi();
        } catch (IOException e) {
            showApiError("Could not get content from API, make sure URL is configured and backend up and running > \n" + e.getMessage());
        }
    }

    private void showApiError(String message) {
        TextFlow errorMessage = new TextFlow();
        Text errorText = new Text(message);
        errorText.setFill(Color.RED);
        errorMessage.getChildren().add(errorText);
        logger.error("ApiHandler is null");
        this.getChildren().removeAll();
        this.getChildren().add(errorMessage);
    }

    private void getContentFromApi() throws IOException {
        Button allRecipesButton = new Button("Refresh");

        this.tagsBar = new ButtonBar();
        tagsBar.getButtons().add(allRecipesButton);
        populateTags(apiHandler.getAllTags());
        this.recipeCards = new SplitPane();
        recipeCards.setOrientation(javafx.geometry.Orientation.VERTICAL);
        this.getChildren().addAll(tagsBar, recipeCards);

        allRecipesButton.setOnAction(event -> {
                tagsBar.getButtons().clear();
                recipeCards.getItems().clear();
                tagsBar.getButtons().add(allRecipesButton);
            try {
                populateTags(apiHandler.getAllTags());
                populateList(apiHandler.getAllRecipes(), recipeCards);
            } catch (IOException e) {
                showApiError("Could not get content from API, make sure URL is configured and backend up and running > \n" + e.getMessage());
            }


        });
    }

    private void populateTags(ArrayList<String> tags) {
        for(String tag : tags) {
            Button tagButton = getButton(apiHandler, tag);
            tagsBar.getButtons().add(tagButton);
        }
    }

    private Button getButton(ApiHandler apiHandler, String tag) {
        Button tagButton = new Button(tag);
        tagButton.setOnAction(event -> {
            try {
                recipeCards.getItems().clear();
                populateList(apiHandler.getRecipesByTag(tag), recipeCards);
            } catch (IOException e) {
                showApiError("Could not get content from API, make sure URL is configured and backend up and running > \n " + e.getMessage());
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