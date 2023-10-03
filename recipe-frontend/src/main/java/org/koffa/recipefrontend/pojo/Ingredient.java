package org.koffa.recipefrontend.pojo;

import lombok.Data;

@Data
public class Ingredient {
    private String name;
    private String unit;
    private double amount;
}