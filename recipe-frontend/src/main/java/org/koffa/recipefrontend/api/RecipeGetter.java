package org.koffa.recipefrontend.api;

import org.koffa.recipefrontend.pojo.Recipe;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public interface RecipeGetter {
    Recipe getRecipe(int id) throws IOException;
    List<Recipe> getAllRecipes() throws IOException;

    ArrayList<String> getAllTags() throws IOException;

    List<Recipe> getRecipesByTag(String tag) throws IOException;
}
