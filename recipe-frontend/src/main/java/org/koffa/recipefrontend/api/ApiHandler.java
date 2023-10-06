package org.koffa.recipefrontend.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.koffa.recipefrontend.pojo.ChatMessage;
import org.koffa.recipefrontend.pojo.Recipe;
import org.springframework.stereotype.Service;
@Service
public class ApiHandler implements RecipeSender, RecipeGetter, ChatMessageGetter {
    private final URL apiUrl;
    public ApiHandler(URL apiUrl) {
        this.apiUrl = apiUrl;
    }

    @Override
    public String send(Recipe recipe) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL(apiUrl + "/recipe/publish").openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);
        connection.getOutputStream().write(recipe.toJson().getBytes());
        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
        StringBuilder response = new StringBuilder();
        while(br.ready()) {
            response.append(br.readLine());
        }
        connection.disconnect();
        return response.toString();
    }

    @Override
    public Recipe getRecipe(int id) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL(apiUrl + "/recipe/get/" + id).openConnection();
        return Recipe.fromJson(getString(connection));
    }

    @Override
    public List<Recipe> getAllRecipes() throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL(apiUrl + "/recipe/get").openConnection();
        Gson gson = new Gson();
        return gson.fromJson(getString(connection), new TypeToken<List<Recipe>>(){}.getType());
    }

    @Override
    public ArrayList<String> getAllTags() throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL(apiUrl + "/recipe/get/tag").openConnection();
        Gson gson = new Gson();
        return gson.fromJson(getString(connection), new TypeToken<ArrayList<String>>(){}.getType());
    }

    @Override
    public List<Recipe> getRecipesByTag(String tag) throws IOException {
        String encodedTag = URLEncoder.encode(tag, StandardCharsets.UTF_8).replace("+", "%20");
        HttpURLConnection connection = (HttpURLConnection) new URL(apiUrl + "/recipe/get/tag/" + encodedTag).openConnection();
        String jsonResult = getString(connection);
        Gson gson = new Gson();
        return gson.fromJson(jsonResult, new TypeToken<List<Recipe>>(){}.getType());
    }

    private static String getString(HttpURLConnection connection) throws IOException {
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);
        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
        StringBuilder response = new StringBuilder();
        while(br.ready()) {
            response.append(br.readLine());
        }
        br.close();
        connection.disconnect();
        return response.toString();
    }

    @Override
    public List<ChatMessage> getRecipeChatMessages(long recipeId) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL(apiUrl + "/chat/get/" + recipeId).openConnection();
        Gson gson = new Gson();
        return gson.fromJson(getString(connection), new TypeToken<List<ChatMessage>>(){}.getType());
    }
    public Recipe updateRecipe(Recipe recipe) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL(apiUrl + "/recipe/update/" + recipe.getId()).openConnection();
        connection.setRequestMethod("PUT");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);
        connection.getOutputStream().write(recipe.toJson().getBytes());
        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
        StringBuilder response = new StringBuilder();
        while (br.ready()) {
            response.append(br.readLine());
        }
        connection.disconnect();
        return Recipe.fromJson(response.toString());
    }
    public void deleteRecipe(long id) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL(apiUrl + "/recipe/delete/" + id).openConnection();
        connection.setRequestMethod("DELETE");
        connection.getResponseCode();
        connection.disconnect();
    }
}