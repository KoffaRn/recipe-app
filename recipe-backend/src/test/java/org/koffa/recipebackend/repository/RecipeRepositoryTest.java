package org.koffa.recipebackend.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.koffa.recipebackend.entity.Recipe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(classes = {RecipeRepository.class})
@EnableAutoConfiguration
@EntityScan(basePackages = {"org.koffa.recipebackend.entity"})
@DataJpaTest
class RecipeRepositoryTest {
    @Autowired
    private RecipeRepository recipeRepository;

    /**
     * Method under test: {@link RecipeRepository#findAllUniqueTags()}
     */
    @Test
    void testFindAllUniqueTags() {
        Recipe recipe = new Recipe();
        recipe.setDescription("The characteristics of someone or something");
        recipe.setIngredients(new ArrayList<>());
        recipe.setName("Name");
        recipe.setSteps(new ArrayList<>());
        recipe.setTags(new ArrayList<>(){
            {
                add("Tag");
            }
        });

        Recipe recipe2 = new Recipe();
        recipe2.setDescription("Description");
        recipe2.setIngredients(new ArrayList<>());
        recipe2.setName("org.koffa.recipebackend.entity.Recipe");
        recipe2.setSteps(new ArrayList<>());
        recipe2.setTags(new ArrayList<>(){
            {
                add("Tag1");
            }
        });
        recipeRepository.save(recipe);
        recipeRepository.save(recipe2);
        assertEquals(2, recipeRepository.findAllUniqueTags().size());
    }

    /**
     * Method under test: {@link RecipeRepository#findAllByTag(String)}
     */
    @Test
    void testFindAllByTag() {
        Recipe recipe = new Recipe();
        recipe.setDescription("The characteristics of someone or something");
        recipe.setIngredients(new ArrayList<>());
        recipe.setName("Name");
        recipe.setSteps(new ArrayList<>());
        recipe.setTags(new ArrayList<>(){
            {
                add("Tag");
            }
        });

        Recipe recipe2 = new Recipe();
        recipe2.setDescription("Description");
        recipe2.setIngredients(new ArrayList<>());
        recipe2.setName("org.koffa.recipebackend.entity.Recipe");
        recipe2.setSteps(new ArrayList<>());
        recipe2.setTags(new ArrayList<>());
        recipeRepository.save(recipe);
        recipeRepository.save(recipe2);
        assertEquals(recipe, recipeRepository.findAllByTag("Tag").get(0));
    }
}