package org.koffa.recipefrontend.textformatter;

import javafx.util.converter.DoubleStringConverter;

public class PositiveIntegerStringConverter extends DoubleStringConverter {
    @Override
    public Double fromString(String value) {
        double result = super.fromString(value);
        return Math.max(result, 0);
    }
    @Override
    public String toString(Double value) {
        if (value < 0) {
            return "0";
        }
        return super.toString(value);
    }
}