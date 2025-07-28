package com.demo.apiconsumer.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {
    @Value("${github.token}")
    String githubToken;
    @Value("${github.baseUrl}")
    private String githubApiBaseUrl;

    @Bean
    public RestClient githubRestClient() {
        return RestClient.builder()
                .baseUrl(githubApiBaseUrl)
                .defaultHeader("Accept", "application/vnd.github+json")
                .defaultHeader("Authorization", "Bearer " + githubToken)
                .build();
    }
}
