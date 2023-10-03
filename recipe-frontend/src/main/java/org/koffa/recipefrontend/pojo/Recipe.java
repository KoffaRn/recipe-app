package org.koffa.recipefrontend.pojo;

import com.google.gson.Gson;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
@Scope("prototype")
public class Recipe {
    private Long id;
    private String name;
    private String description;
    private List<Ingredient> ingredients;
    private List<String> steps;
    private List<String> tags;
    public static Recipe fromJson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, Recipe.class);
    }
    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}