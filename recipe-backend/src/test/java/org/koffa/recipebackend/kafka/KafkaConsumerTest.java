package org.koffa.recipebackend.kafka;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.koffa.recipebackend.entity.ChatMessage;
import org.koffa.recipebackend.entity.Ingredient;
import org.koffa.recipebackend.entity.Recipe;
import org.koffa.recipebackend.service.ChatMessageService;
import org.koffa.recipebackend.service.RecipeService;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {KafkaConsumer.class})
@ExtendWith(SpringExtension.class)
class KafkaConsumerTest {
    @MockBean
    private ChatMessageService chatMessageService;

    @Autowired
    private KafkaConsumer kafkaConsumer;

    @MockBean
    private RecipeService recipeService;

    /**
     * Method under test: {@link KafkaConsumer#consume(ChatMessage)}
     */
    @Test
    void testConsume() {
        doNothing().when(chatMessageService).save(Mockito.<ChatMessage>any());

        ChatMessage message = new ChatMessage();
        message.setId(1L);
        message.setMessage("Not all who wander are lost");
        message.setRecipeId(1L);
        message.setSender("Sender");
        message.setTimestamp(mock(Timestamp.class));
        kafkaConsumer.consume(message);
        verify(chatMessageService).save(Mockito.<ChatMessage>any());
        assertEquals(1L, message.getId());
        assertEquals("Sender", message.getSender());
        assertEquals(1L, message.getRecipeId().longValue());
        assertEquals("Not all who wander are lost", message.getMessage());
    }

    /**
     * Method under test: {@link KafkaConsumer#consume(Recipe)}
     */
    @Test
    void testConsume2() {
        Recipe recipe = new Recipe();
        recipe.setDescription("The characteristics of someone or something");
        recipe.setId(1L);
        ArrayList<Ingredient> ingredients = new ArrayList<>();
        recipe.setIngredients(ingredients);
        recipe.setName("Name");
        recipe.setSteps(new ArrayList<>());
        recipe.setTags(new ArrayList<>());
        when(recipeService.save(Mockito.<Recipe>any())).thenReturn(recipe);

        Recipe recipe2 = new Recipe();
        recipe2.setDescription("The characteristics of someone or something");
        recipe2.setId(1L);
        recipe2.setIngredients(new ArrayList<>());
        recipe2.setName("Name");
        recipe2.setSteps(new ArrayList<>());
        recipe2.setTags(new ArrayList<>());
        kafkaConsumer.consume(recipe2);
        verify(recipeService).save(Mockito.<Recipe>any());
        assertEquals("The characteristics of someone or something", recipe2.getDescription());
        assertEquals(ingredients, recipe2.getTags());
        assertEquals(ingredients, recipe2.getSteps());
        assertEquals("Name", recipe2.getName());
        assertEquals(ingredients, recipe2.getIngredients());
        assertEquals(1L, recipe2.getId().longValue());
    }

    /**
     * Method under test: {@link KafkaConsumer#consume(Recipe)}
     */
    @Test
    void testConsume3() {
        Recipe recipe = new Recipe();
        recipe.setDescription("The characteristics of someone or something");
        recipe.setId(1L);
        recipe.setIngredients(new ArrayList<>());
        recipe.setName("Name");
        recipe.setSteps(new ArrayList<>());
        recipe.setTags(new ArrayList<>());
        when(recipeService.save(Mockito.<Recipe>any())).thenReturn(recipe);
        Recipe recipe2 = mock(Recipe.class);
        doNothing().when(recipe2).setDescription(Mockito.<String>any());
        doNothing().when(recipe2).setId(Mockito.<Long>any());
        doNothing().when(recipe2).setIngredients(Mockito.<List<Ingredient>>any());
        doNothing().when(recipe2).setName(Mockito.<String>any());
        doNothing().when(recipe2).setSteps(Mockito.<List<String>>any());
        doNothing().when(recipe2).setTags(Mockito.<List<String>>any());
        recipe2.setDescription("The characteristics of someone or something");
        recipe2.setId(1L);
        recipe2.setIngredients(new ArrayList<>());
        recipe2.setName("Name");
        recipe2.setSteps(new ArrayList<>());
        recipe2.setTags(new ArrayList<>());
        kafkaConsumer.consume(recipe2);
        verify(recipeService).save(Mockito.<Recipe>any());
        verify(recipe2).setDescription(Mockito.<String>any());
        verify(recipe2).setId(Mockito.<Long>any());
        verify(recipe2).setIngredients(Mockito.<List<Ingredient>>any());
        verify(recipe2).setName(Mockito.<String>any());
        verify(recipe2).setSteps(Mockito.<List<String>>any());
        verify(recipe2).setTags(Mockito.<List<String>>any());
    }
}

