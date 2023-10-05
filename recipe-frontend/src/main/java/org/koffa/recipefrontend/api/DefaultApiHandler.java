package org.koffa.recipefrontend.api;

import org.koffa.recipefrontend.pojo.ChatMessage;
import org.koffa.recipefrontend.pojo.Recipe;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DefaultApiHandler extends ApiHandler{
    @Override
    public String send(Recipe recipe) throws IOException {
        return null;
    }

    @Override
    public Recipe getRecipe(int id) throws IOException {
        return null;
    }

    @Override
    public List<Recipe> getAllRecipes() throws IOException {
        return null;
    }

    @Override
    public ArrayList<String> getAllTags() throws IOException {
        return null;
    }

    @Override
    public List<Recipe> getRecipesByTag(String tag) throws IOException {
        return null;
    }

    @Override
    public List<ChatMessage> getRecipeChatMessages(long recipeId) throws IOException {
        return null;
    }
}
