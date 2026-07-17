package com.bbots.mfin.dto;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .baseUrl("http://localhost:8086/accessmanager")
                .filter((request, next) -> {
                    System.out.println("👉 Request URL: " + request.url());
                    return next.exchange(request);
                })
                .build();
    }
}
