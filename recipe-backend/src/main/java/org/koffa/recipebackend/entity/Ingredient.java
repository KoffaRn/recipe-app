package org.koffa.recipebackend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String unit;
    private double amount;
}