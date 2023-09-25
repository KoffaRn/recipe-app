package org.koffa.recipebackend.config;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.koffa.recipebackend.entity.Recipe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.io.IOException;

public class RecipeJsonDeseriazlier extends JsonDeserializer<Recipe>  {
    private final Logger logger =LoggerFactory.getLogger(RecipeJsonDeseriazlier.class);
    public RecipeJsonDeseriazlier() {
        super();
        this.addTrustedPackages("org.koffa.recipebackend.entity");
        this.setRemoveTypeHeaders(false);
        this.setUseTypeMapperForKey(true);
    }
@Override
    public Recipe deserialize(String topic, byte[] data) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            System.err.println("Deserializing data");
            return mapper.readValue(data, Recipe.class);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }
}