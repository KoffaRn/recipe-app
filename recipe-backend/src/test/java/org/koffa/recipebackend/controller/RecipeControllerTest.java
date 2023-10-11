package org.koffa.recipebackend.controller;

import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.koffa.recipebackend.entity.Recipe;
import org.koffa.recipebackend.kafka.KafkaProducer;
import org.koffa.recipebackend.repository.RecipeRepository;
import org.koffa.recipebackend.service.ChatMessageService;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {RecipeController.class})
@ExtendWith(SpringExtension.class)
class RecipeControllerTest {
    @MockBean
    private ChatMessageService chatMessageService;

    @MockBean
    private KafkaProducer kafkaProducer;

    @Autowired
    private RecipeController recipeController;

    @MockBean
    private RecipeRepository recipeRepository;

    /**
     * Method under test: {@link RecipeController#delete(long)}
     */
    @Test
    void testDelete() throws Exception {
        doNothing().when(recipeRepository).deleteById(Mockito.<Long>any());
        doNothing().when(chatMessageService).deleteByRecipeId(anyLong());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/v1/recipe/delete/{id}", 1L);
        MockMvcBuilders.standaloneSetup(recipeController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Recipe deleted"));
    }

    /**
     * Method under test: {@link RecipeController#delete(long)}
     */
    @Test
    void testDelete2() throws Exception {
        doNothing().when(recipeRepository).deleteById(Mockito.<Long>any());
        doNothing().when(chatMessageService).deleteByRecipeId(anyLong());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/v1/recipe/delete/{id}", 1L);
        requestBuilder.contentType("https://example.org/example");
        MockMvcBuilders.standaloneSetup(recipeController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Recipe deleted"));
    }

    /**
     * Method under test: {@link RecipeController#getAll()}
     */
    @Test
    void testGetAll() throws Exception {
        when(recipeRepository.findAll()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/recipe/get");
        MockMvcBuilders.standaloneSetup(recipeController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Method under test: {@link RecipeController#getAllByTag(String)}
     */
    @Test
    void testGetAllByTag() throws Exception {
        when(recipeRepository.findAllByTag(Mockito.<String>any())).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/recipe/get/tag/{tag}", "Tag");
        MockMvcBuilders.standaloneSetup(recipeController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Method under test: {@link RecipeController#getAllByTag(String)}
     */
    @Test
    void testGetAllByTag2() throws Exception {
        when(recipeRepository.findAllUniqueTags()).thenReturn(new ArrayList<>());
        when(recipeRepository.findAllByTag(Mockito.<String>any())).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/recipe/get/tag/{tag}", "",
                "Uri Variables");
        MockMvcBuilders.standaloneSetup(recipeController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Method under test: {@link RecipeController#getAllTags()}
     */
    @Test
    void testGetAllTags() throws Exception {
        when(recipeRepository.findAllUniqueTags()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/recipe/get/tag");
        MockMvcBuilders.standaloneSetup(recipeController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Method under test: {@link RecipeController#getOne(long)}
     */
    @Test
    void testGetOne() throws Exception {
        Recipe recipe = new Recipe();
        recipe.setDescription("The characteristics of someone or something");
        recipe.setId(1L);
        recipe.setIngredients(new ArrayList<>());
        recipe.setName("Name");
        recipe.setSteps(new ArrayList<>());
        recipe.setTags(new ArrayList<>());
        Optional<Recipe> ofResult = Optional.of(recipe);
        when(recipeRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/recipe/get/{id}", 1L);
        MockMvcBuilders.standaloneSetup(recipeController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":1,\"name\":\"Name\",\"description\":\"The characteristics of someone or something\",\"ingredients\":[],"
                                        + "\"steps\":[],\"tags\":[]}"));
    }

    /**
     * Method under test: {@link RecipeController#getOne(long)}
     */
    @Test
    void testGetOne2() throws Exception {
        Optional<Recipe> emptyResult = Optional.empty();
        when(recipeRepository.findById(Mockito.<Long>any())).thenReturn(emptyResult);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/recipe/get/{id}", 1L);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(recipeController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(500));
    }

    /**
     * Method under test: {@link RecipeController#publish(Recipe)}
     */
    @Test
    void testPublish() throws Exception {
        doNothing().when(kafkaProducer).sendMessage(Mockito.<Recipe>any());

        Recipe recipe = new Recipe();
        recipe.setDescription("The characteristics of someone or something");
        recipe.setId(1L);
        recipe.setIngredients(new ArrayList<>());
        recipe.setName("Name");
        recipe.setSteps(new ArrayList<>());
        recipe.setTags(new ArrayList<>());
        String content = (new ObjectMapper()).writeValueAsString(recipe);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/recipe/publish")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(recipeController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "Message sent to kafka: Recipe(id=1, name=Name, description=The characteristics of someone or something,"
                                        + " ingredients=[], steps=[], tags=[])"));
    }

    /**
     * Method under test: {@link RecipeController#update(long, Recipe)}
     */
    @Test
    void testUpdate() throws Exception {
        Recipe recipe = new Recipe();
        recipe.setDescription("The characteristics of someone or something");
        recipe.setId(1L);
        recipe.setIngredients(new ArrayList<>());
        recipe.setName("Name");
        recipe.setSteps(new ArrayList<>());
        recipe.setTags(new ArrayList<>());
        Optional<Recipe> ofResult = Optional.of(recipe);

        Recipe recipe2 = new Recipe();
        recipe2.setDescription("The characteristics of someone or something");
        recipe2.setId(1L);
        recipe2.setIngredients(new ArrayList<>());
        recipe2.setName("Name");
        recipe2.setSteps(new ArrayList<>());
        recipe2.setTags(new ArrayList<>());
        when(recipeRepository.save(Mockito.<Recipe>any())).thenReturn(recipe2);
        when(recipeRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        Recipe recipe3 = new Recipe();
        recipe3.setDescription("The characteristics of someone or something");
        recipe3.setId(1L);
        recipe3.setIngredients(new ArrayList<>());
        recipe3.setName("Name");
        recipe3.setSteps(new ArrayList<>());
        recipe3.setTags(new ArrayList<>());
        String content = (new ObjectMapper()).writeValueAsString(recipe3);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/v1/recipe/update/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(recipeController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":1,\"name\":\"Name\",\"description\":\"The characteristics of someone or something\",\"ingredients\":[],"
                                        + "\"steps\":[],\"tags\":[]}"));
    }

    /**
     * Method under test: {@link RecipeController#update(long, Recipe)}
     */
    @Test
    void testUpdate2() throws Exception {
        Recipe recipe = new Recipe();
        recipe.setDescription("The characteristics of someone or something");
        recipe.setId(1L);
        recipe.setIngredients(new ArrayList<>());
        recipe.setName("Name");
        recipe.setSteps(new ArrayList<>());
        recipe.setTags(new ArrayList<>());
        when(recipeRepository.save(Mockito.<Recipe>any())).thenReturn(recipe);
        Optional<Recipe> emptyResult = Optional.empty();
        when(recipeRepository.findById(Mockito.<Long>any())).thenReturn(emptyResult);

        Recipe recipe2 = new Recipe();
        recipe2.setDescription("The characteristics of someone or something");
        recipe2.setId(1L);
        recipe2.setIngredients(new ArrayList<>());
        recipe2.setName("Name");
        recipe2.setSteps(new ArrayList<>());
        recipe2.setTags(new ArrayList<>());
        String content = (new ObjectMapper()).writeValueAsString(recipe2);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/v1/recipe/update/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(recipeController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(500));
    }
}

