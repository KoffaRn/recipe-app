package org.koffa.recipefrontend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.MalformedURLException;
import java.net.URL;
@Configuration
public class UrlConfig {
    @Value(value = "${recipe-api.url}")
    private String url;
    @Bean
    public URL getApiUrl() {
        try {
            return new URL(url);
        } catch (MalformedURLException e) {
            return null;
        }
    }
}
