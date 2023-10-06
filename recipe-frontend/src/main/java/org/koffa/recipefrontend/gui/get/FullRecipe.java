package org.koffa.recipefrontend.gui.get;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import org.koffa.recipefrontend.chat.ChatWebSocketClient;
import org.koffa.recipefrontend.api.ApiHandler;
import org.koffa.recipefrontend.pojo.ChatMessage;
import org.koffa.recipefrontend.pojo.Ingredient;
import org.koffa.recipefrontend.pojo.Message;
import org.koffa.recipefrontend.pojo.Recipe;
import org.koffa.recipefrontend.textformatter.PositiveDoubleFilter;
import org.springframework.beans.factory.annotation.Value;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class is responsible for displaying a full recipe.
 */
public class FullRecipe extends Application {
    private final String chatUrl;
    private final Recipe recipe;
    private final TextFlow chat = new TextFlow();
    private ChatWebSocketClient client;
    private TextField chatMessage = new TextField();
    private TextField name = new TextField("Name");
    private final ApiHandler apiHandler;
    private final SplitPane ingredients = new SplitPane();
    private TextField recipeName;
    private TextField recipeDescription;
    private final SplitPane steps = new SplitPane();
    private final SplitPane tags = new SplitPane();


    public FullRecipe(ApiHandler apiHandler, Recipe recipe, @Value(value = "${websocket.url}") String chatUrl) {
        this.apiHandler = apiHandler;
        this.recipe = recipe;
        this.chatUrl = chatUrl;
    }


    @Override
    public void start(Stage stage) throws Exception {
        // Instantiate the client and connect to the websocket
        client = new ChatWebSocketClient(chatUrl, this);
        client.connect();
        client.subscribe(recipe.getId());
        // Create the UI
        ButtonBar buttonBar = new ButtonBar();
        Button updateButton = new Button("Update");
        Button deleteButton = new Button("Delete");
        buttonBar.getButtons().addAll(updateButton, deleteButton);
        ScrollPane scrollPane = new ScrollPane();
        VBox root = new VBox();
        recipeName = new TextField(recipe.getName());
        recipeDescription = new TextField(recipe.getDescription());
        ingredients.setOrientation(Orientation.VERTICAL);
        for(Ingredient ingredient : recipe.getIngredients()) {
            TextField ingredientName = new TextField(ingredient.getName());
            TextField ingredientAmount = new TextField(ingredient.getAmount()+"");
            ingredientAmount.setTextFormatter(new TextFormatter<>(new PositiveDoubleFilter()));
            TextField ingredientUnit = new TextField(ingredient.getUnit());
            VBox ingredientBox = new VBox();
            ingredientBox.getChildren().addAll(ingredientName, ingredientAmount, ingredientUnit);
            ingredients.getItems().add(ingredientBox);
        }
        steps.setOrientation(javafx.geometry.Orientation.VERTICAL);
        for(String step : recipe.getSteps()) {
            TextField instructionLabel = new TextField(step);
            steps.getItems().add(instructionLabel);
        }
        tags.setOrientation(javafx.geometry.Orientation.VERTICAL);
        for(String tag : recipe.getTags()) {
            TextField tagLabel = new TextField(tag);
            tags.getItems().add(tagLabel);
        }

        Label chatLabel = new Label("Chat");
        SplitPane chatPane = new SplitPane();
        name = new TextField("Name");
        chatMessage = new TextField();
        chatPane.getItems().addAll(name, chatMessage);
        chatPane.setDividerPosition(0, 0.2);
        chatMessage.setOnAction(actionEvent -> sendMessage());
        deleteButton.setOnAction(actionEvent -> deleteRecipe(stage));
        updateButton.setOnAction(actionEvent -> updateRecipe());
        addDbMessages();
        ScrollPane chatScroll = new ScrollPane();
        chatScroll.setContent(chat);
        chat.setMaxWidth(350);
        chatScroll.setMaxWidth(370);
        chatScroll.setMaxHeight(200);
        // Scroll to bottom of chat when new message is added
        chatScroll.vvalueProperty().bind(chat.heightProperty());
        root.getChildren().addAll(buttonBar, recipeName, recipeDescription,ingredients,steps,tags,chatLabel,chatScroll,chatPane);
        scrollPane.setContent(root);
        stage.setScene(new javafx.scene.Scene(scrollPane, 400, 500));
        stage.setTitle(recipe.getName());
        stage.show();
    }

    private void updateRecipe() {
        try {
            apiHandler.updateRecipe(getCurrentRecipe());
            addInfoMessage("Recipe updated");
        } catch (IOException e) {
            addErrorMessage("Could not update recipe > " + e.getMessage());
        }
    }

    private Recipe getCurrentRecipe() {
        Recipe recipe = new Recipe();
        recipe.setId(this.recipe.getId());
        recipe.setName(recipeName.getText());
        recipe.setDescription(recipeDescription.getText());
        recipe.setTags(getTags());
        recipe.setIngredients(getIngredients());
        recipe.setSteps(getSteps());
        return recipe;
    }

    private List<String> getSteps() {
        List<String> stepStrings = new ArrayList<>();
        for(Node node : steps.getItems()) {
            TextField step = (TextField) node;
            stepStrings.add(step.getText());
        }
        return stepStrings;
    }

    private List<Ingredient> getIngredients() {
        List<Ingredient> ingredients = new ArrayList<>();
        for(Node node : this.ingredients.getItems()) {
            VBox ingredientBox = (VBox) node;
            TextField name = (TextField) ingredientBox.getChildren().get(0);
            TextField amount = (TextField) ingredientBox.getChildren().get(1);
            TextField unit = (TextField) ingredientBox.getChildren().get(2);
            Ingredient ingredient = new Ingredient();
            ingredient.setName(name.getText());
            ingredient.setAmount(Double.parseDouble(amount.getText()));
            ingredient.setUnit(unit.getText());
            ingredients.add(ingredient);
        }
        return ingredients;
    }

    private List<String> getTags() {
        List<String> tagStrings = new ArrayList<>();
        for(Node node : tags.getItems()) {
            TextField tag = (TextField) node;
            tagStrings.add(tag.getText());
        }
        return tagStrings;
    }


    private void deleteRecipe(Stage stage) {
        try {
            apiHandler.deleteRecipe(recipe.getId());
            stage.close();
        } catch (IOException e) {
            addErrorMessage("Could not delete recipe > " + e.getMessage());
        }
    }
    private void addMessage(String message, Color color) {
        Text text = new Text(message + "\n");
        text.setFill(color);
        Platform.runLater(() -> chat.getChildren().add(text));
    }
    private void addErrorMessage(String message) {
        addMessage(message, Color.RED);
    }
    private void addInfoMessage(String message) {
        addMessage(message, Color.GREEN);
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
            List<ChatMessage> messages = apiHandler.getRecipeChatMessages(recipe.getId());
            //Sort by timestamp (see ChatMessages)
            Collections.sort(messages);
            return messages;
        } catch (IOException e) {
            System.err.println(e.getMessage());
            return null;
        }
    }
}
