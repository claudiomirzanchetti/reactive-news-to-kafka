package com.example.reactivenews2kafka.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@AllArgsConstructor
public class WebClientConfig {
    @Bean(name = "hackerNewsApiWebClient")
    public WebClient hackerNewsApiWebClient() {
        return WebClient.create();
    }
}
