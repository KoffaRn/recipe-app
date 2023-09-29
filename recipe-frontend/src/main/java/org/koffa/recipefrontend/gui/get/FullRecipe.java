package org.koffa.recipefrontend.gui.get;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import org.koffa.recipefrontend.chat.ChatWebSocketClient;
import org.koffa.recipefrontend.api.ApiHandler;
import org.koffa.recipefrontend.pojo.ChatMessage;
import org.koffa.recipefrontend.pojo.Ingredient;
import org.koffa.recipefrontend.pojo.Message;
import org.koffa.recipefrontend.pojo.Recipe;
import org.springframework.beans.factory.annotation.Value;


import java.io.IOException;
import java.util.List;

/**
 * This class is responsible for displaying a full recipe.
 */
public class FullRecipe extends Application {
    private final String url;
    private final Recipe recipe;
    private final TextFlow chat = new TextFlow();
    private ChatWebSocketClient client;
    private TextField chatMessage = new TextField();
    private TextField name = new TextField("Name");
    private final ApiHandler apiHandler;

    public FullRecipe(ApiHandler apiHandler, Recipe recipe, @Value(value = "${websocket.url}") String url) {
        this.apiHandler = apiHandler;
        this.recipe = recipe;
        this.url = url;
    }


    @Override
    public void start(Stage stage) throws Exception {
        // Instantiate the client and connect to the websocket
        client = new ChatWebSocketClient(url, this);
        client.connect();
        client.subscribe(recipe.getId());
        // Create the UI
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

        Label chatLabel = new Label("Chat");
        SplitPane chatPane = new SplitPane();
        name = new TextField("Name");
        chatMessage = new TextField();
        chatPane.getItems().addAll(name, chatMessage);
        chatPane.setDividerPosition(0, 0.2);
        chatMessage.setOnAction(actionEvent -> {
            sendMessage();
        });
        addDbMessages();
        VBox chatBox = new VBox(chat, chatPane);
        ScrollPane chatScroll = new ScrollPane();
        chatScroll.setContent(chatBox);
        root.getChildren().addAll(recipeName, recipeDescription,ingredients,steps,tags,chatLabel,chatScroll,chatPane);
        scrollPane.setContent(root);
        stage.setScene(new javafx.scene.Scene(scrollPane, 300, 300));
        stage.setTitle(recipe.getName());
        stage.show();
    }
    // Add all messages from the database to the chat
    private void addDbMessages() {
        List<ChatMessage> chatMessages = getDbMessages();
        if (chatMessages == null) return;
        for(ChatMessage chatMessage : chatMessages) {
            newMessage(chatMessage);
        }
    }
    // Add a new message to the chat (called from the websocket and the database)
    public void newMessage(ChatMessage message) {
        String time = message.getTimestamp().toString().replaceFirst("\\.\\d*(Z)?$", "$1");
        Text text = new Text(time + "> " + message.getSender() + ": " + message.getMessage() + "\n");
        Platform.runLater(() -> chat.getChildren().add(text));
    }
    // Send a message to the websocket
    private void sendMessage() {
        Message message = new Message();
        message.setSender(name.getText());
        message.setMessage(chatMessage.getText());
        client.send(recipe.getId(), message);
        chatMessage.setText("");
    }
    private List<ChatMessage> getDbMessages() {
        try {
            return apiHandler.getRecipeChatMessages(recipe.getId());
        } catch (IOException e) {
            System.err.println(e.getMessage());
            return null;
        }
    }
}
