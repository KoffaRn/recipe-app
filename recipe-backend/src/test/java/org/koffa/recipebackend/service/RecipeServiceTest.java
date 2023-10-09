package org.koffa.recipebackend.service;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.koffa.recipebackend.entity.Recipe;
import org.koffa.recipebackend.repository.RecipeRepository;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {RecipeService.class})
@ExtendWith(SpringExtension.class)
class RecipeServiceTest {
    @MockBean
    private RecipeRepository recipeRepository;

    @Autowired
    private RecipeService recipeService;

    /**
     * Method under test: {@link RecipeService#save(Recipe)}
     */
    @Test
    void testSave() {
        Recipe recipe = new Recipe();
        recipe.setDescription("The characteristics of someone or something");
        recipe.setId(1L);
        recipe.setIngredients(new ArrayList<>());
        recipe.setName("Name");
        recipe.setSteps(new ArrayList<>());
        recipe.setTags(new ArrayList<>());
        when(recipeRepository.save(Mockito.any())).thenReturn(recipe);

        Recipe recipe2 = new Recipe();
        recipe2.setDescription("The characteristics of someone or something");
        recipe2.setId(1L);
        recipe2.setIngredients(new ArrayList<>());
        recipe2.setName("Name");
        recipe2.setSteps(new ArrayList<>());
        recipe2.setTags(new ArrayList<>());
        assertSame(recipe, recipeService.save(recipe2));
        verify(recipeRepository).save(Mockito.any());
    }

    /**
     * Method under test: {@link RecipeService#get(long)}
     */
    @Test
    void testGet() {
        Recipe recipe = new Recipe();
        recipe.setDescription("The characteristics of someone or something");
        recipe.setId(1L);
        recipe.setIngredients(new ArrayList<>());
        recipe.setName("Name");
        recipe.setSteps(new ArrayList<>());
        recipe.setTags(new ArrayList<>());
        when(recipeRepository.getReferenceById(Mockito.<Long>any())).thenReturn(recipe);
        assertSame(recipe, recipeService.get(1L));
        verify(recipeRepository).getReferenceById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link RecipeService#getRecipeById(long)}
     */
    @Test
    void testGetRecipeById() {
        Recipe recipe = new Recipe();
        recipe.setDescription("The characteristics of someone or something");
        recipe.setId(1L);
        recipe.setIngredients(new ArrayList<>());
        recipe.setName("Name");
        recipe.setSteps(new ArrayList<>());
        recipe.setTags(new ArrayList<>());
        Optional<Recipe> ofResult = Optional.of(recipe);
        when(recipeRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        assertSame(recipe, recipeService.getRecipeById(1L));
        verify(recipeRepository).findById(Mockito.<Long>any());
    }
}

