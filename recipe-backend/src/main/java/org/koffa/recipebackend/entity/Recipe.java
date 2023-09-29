package org.koffa.recipebackend.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "recipe")
@Data
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Ingredient> ingredients;
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> steps;
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> tags;
}