package org.koffa.recipebackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
public class RecipeBackendApplication {
	public static void main(String[] args) {
		SpringApplication.run(RecipeBackendApplication.class, args);
	}
}