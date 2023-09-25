package org.koffa.recipefrontend.gui;

import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.util.List;

public class TagBox extends VBox {
    private final SplitPane tags = new SplitPane();
    public TagBox() {
        tags.setOrientation(javafx.geometry.Orientation.VERTICAL);
        Button addTagButton = new Button("Add tag");
        Button removeTag = new Button("remove tag");
        SplitPane tagButtons = new SplitPane();
        tagButtons.getItems().addAll(addTagButton, removeTag);
        tags.getItems().add(new TextField("Write tag here"));
        this.getChildren().addAll(tagButtons, tags);
        addTagButton.setOnAction(event -> tags.getItems().add(new TextField("Write tag here")));
        removeTag.setOnAction(event -> {
            if (tags.getItems().size() > 1) {
                tags.getItems().remove(tags.getItems().size() - 1);
            }
        });
    }
    public List<String> getTags() {
        return tags.getItems().stream().map(node -> ((TextField) node).getText()).toList();
    }
}