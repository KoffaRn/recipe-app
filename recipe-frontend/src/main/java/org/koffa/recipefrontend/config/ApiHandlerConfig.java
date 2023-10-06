package org.koffa.recipefrontend.config;

import org.koffa.recipefrontend.api.ApiHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.URL;

@Configuration
public class ApiHandlerConfig {
    @Value(value = "${recipe-api.url}")
    private String url;
    @Bean
    public ApiHandler getApiHandler() {

        try {
            return new ApiHandler(new URL(url));
        } catch (Exception e) {
            return null;
        }
    }
}