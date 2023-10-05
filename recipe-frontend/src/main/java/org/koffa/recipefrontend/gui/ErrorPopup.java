package org.koffa.recipefrontend.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ErrorPopup extends Application {
    private final String message;

    public ErrorPopup(String message) {
        this.message = message;
    }

    @Override
    public void start(Stage stage) {
        VBox root = new VBox();
        root.getChildren().add(new Label(message));
        stage.setScene(new Scene(root, 300, 300));
        stage.show();
    }
}
