package org.koffa.recipefrontend.config;

import org.koffa.recipefrontend.api.ApiHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiHandlerConfig {

    @Bean
    public ApiHandler getApiHandler() {
        return new ApiHandler();
    }
}