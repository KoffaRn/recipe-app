package org.koffa.recipebackend.repository;

import org.koffa.recipebackend.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    @Query("SELECT DISTINCT r.tags FROM Recipe r")
    List<String> findAllUniqueTags();

    @Query("SELECT r FROM Recipe r WHERE ?1 MEMBER OF r.tags")
    List<Recipe> findAllByTag(String tag);
}