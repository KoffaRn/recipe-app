package org.koffa.recipefrontend.gui.get;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import org.koffa.recipefrontend.ChatWebSocketClient;
import org.koffa.recipefrontend.helper.FullRecipeBuilder;
import org.koffa.recipefrontend.pojo.Ingredient;
import org.koffa.recipefrontend.pojo.Message;
import org.koffa.recipefrontend.pojo.Recipe;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class FullRecipe extends Application {
    private String url;
    private Recipe recipe;
    private TextFlow chat;
    private ChatWebSocketClient client;
    private TextField chatMessage;
    private FullRecipe() {

    }
    public FullRecipe(FullRecipeBuilder fullrecipeBuilder) {
        this.recipe = fullrecipeBuilder.getRecipe();
        this.url = fullrecipeBuilder.getUrl();
    }
    @Override
    public void start(Stage stage) throws Exception {
        client = new ChatWebSocketClient(url, this);
        client.connect();
        client.subscribe(recipe.getId());
        ScrollPane scrollPane = new ScrollPane();
        VBox root = new VBox();
        Label recipeName = new Label(recipe.getName());
        Label recipeDescription = new Label(recipe.getDescription());
        SplitPane ingredients = new SplitPane();
        ingredients.setOrientation(javafx.geometry.Orientation.VERTICAL);
        for(Ingredient ingredient : recipe.getIngredients()) {
            Label ingredientName = new Label(ingredient.getName());
            Label ingredientAmount = new Label(ingredient.getAmount()+"");
            Label ingredientUnit = new Label(ingredient.getUnit());
            VBox ingredientBox = new VBox();
            ingredientBox.getChildren().addAll(ingredientName, ingredientAmount, ingredientUnit);
            ingredients.getItems().add(ingredientBox);
        }
        SplitPane steps = new SplitPane();
        steps.setOrientation(javafx.geometry.Orientation.VERTICAL);
        for(String step : recipe.getSteps()) {
            Label instructionLabel = new Label(step);
            steps.getItems().add(instructionLabel);
        }
        SplitPane tags = new SplitPane();
        tags.setOrientation(javafx.geometry.Orientation.VERTICAL);
        for(String tag : recipe.getTags()) {
            Label tagLabel = new Label(tag);
            tags.getItems().add(tagLabel);
        }
        chat = new TextFlow();
        chatMessage = new TextField();
        Button sendButton = new Button("Send");
        sendButton.setOnAction(actionEvent -> {
            sendMessage();
            chatMessage.setText("");
        });
        root.getChildren().addAll(recipeName, recipeDescription,ingredients,steps,tags,chat,chatMessage,sendButton);
        scrollPane.setContent(root);
        stage.setScene(new javafx.scene.Scene(scrollPane, 300, 300));
        stage.setTitle(recipe.getName());
        stage.show();
    }
    public void newMessage(Message message) {
        Text text = new Text(message.getSender() + ": " + message.getMessage() + "\n");
        Platform.runLater(() -> chat.getChildren().add(text));
    }
    private void sendMessage() {
        Message message = new Message();
        message.setSender("TestSender");
        message.setMessage(chatMessage.getText());
        client.send(recipe.getId(), message);
    }
}
