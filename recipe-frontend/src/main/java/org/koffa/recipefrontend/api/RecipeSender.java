package org.koffa.recipefrontend.api;

import org.koffa.recipefrontend.pojo.Recipe;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public interface RecipeSender {
    String send(Recipe recipe) throws IOException;
}
