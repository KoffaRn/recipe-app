package org.koffa.recipefrontend;

import javafx.application.Application;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RecipeFrontendApplication {

    public static void main(String[] args) {
        Application.launch(MainUi.class, args);
    }

}
