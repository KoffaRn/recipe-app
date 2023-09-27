package org.koffa.recipefrontend.gui.send;


import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class RecipeBox extends VBox {
    private final TextField receipeNameTextField = new TextField("Name of recipe here");
    private final TextArea recipeDescriptionTextArea = new TextArea("Description of recipe here");
    private final RecipeStepsBox recipeStepsBox = new RecipeStepsBox();
    public RecipeBox() {
        Label recipeDescription = new Label("Description of recipe");
        Label recipeName = new Label("Name of recipe");
        this.getChildren().addAll(recipeName, receipeNameTextField, recipeDescription, recipeDescriptionTextArea, recipeStepsBox);
    }
    public ArrayList<String> getSteps() {
        ArrayList<String> steps = new ArrayList<>();
        for (Node node : recipeStepsBox.getSteps()) {
            steps.add(((TextField) node).getText());
        }
        return steps;
    }
    public String getRecipeName() {
        return receipeNameTextField.getText();
    }
    public String getRecipeDescription() {
        return recipeDescriptionTextArea.getText();
    }
}