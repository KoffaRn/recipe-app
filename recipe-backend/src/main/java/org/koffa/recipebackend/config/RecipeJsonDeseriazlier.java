package org.koffa.recipebackend.config;

import org.koffa.recipebackend.entity.Recipe;
import org.springframework.kafka.support.serializer.JsonDeserializer;

public class RecipeJsonDeseriazlier extends JsonDeserializer<Recipe> {
    public RecipeJsonDeseriazlier() {
        super();
        this.addTrustedPackages("org.koffa.recipebackend.entity");
        this.setRemoveTypeHeaders(false);
        this.setUseTypeMapperForKey(true);
    }
}
