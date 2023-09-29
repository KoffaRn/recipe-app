package org.koffa.recipefrontend.gui.send;

import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import org.koffa.recipefrontend.pojo.Ingredient;
import org.koffa.recipefrontend.textformatter.PositiveDoubleFilter;

public class IngredientPane extends SplitPane {
    public IngredientPane() {
        TextFormatter<Integer> textFormatter = new TextFormatter<>(new PositiveDoubleFilter());
        this.setDividerPositions(0.1,0.2,0.9);
        TextField amount = new TextField("1.0");
        amount.setTextFormatter(textFormatter);
        this.getItems().addAll(amount, new TextField("Unit"), new TextField("Name of ingredient"));
    }

    public Ingredient getIngredient() {
        Ingredient ingredient = new Ingredient();
        ingredient.setAmount(Double.parseDouble(((TextField) this.getItems().get(0)).getText()));
        ingredient.setUnit(((TextField) this.getItems().get(1)).getText());
        ingredient.setName(((TextField) this.getItems().get(2)).getText());
        return ingredient;
    }
}