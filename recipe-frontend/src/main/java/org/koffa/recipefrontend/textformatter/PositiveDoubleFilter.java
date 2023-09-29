package org.koffa.recipefrontend.textformatter;


import javafx.scene.control.TextFormatter;

import java.util.function.Function;
import java.util.function.UnaryOperator;

/**
 * This class is used to filter out non-numeric characters from a text field (accepts doubles).
 */
public class PositiveDoubleFilter implements UnaryOperator<TextFormatter.Change> {

    @Override
    public TextFormatter.Change apply(TextFormatter.Change change) {
        if(change.getControlNewText().matches("-?(([1-9][0-9]*)|0)?(\\.[0-9]*)?")) {
            return change;
        }
        return null;
    }

    @Override
    public <V> Function<V, TextFormatter.Change> compose(Function<? super V, ? extends TextFormatter.Change> before) {
        return UnaryOperator.super.compose(before);
    }

    @Override
    public <V> Function<TextFormatter.Change, V> andThen(Function<? super TextFormatter.Change, ? extends V> after) {
        return UnaryOperator.super.andThen(after);
    }
}