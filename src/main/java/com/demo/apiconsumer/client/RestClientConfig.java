package com.demo.apiconsumer.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

@Configuration
public class RestClientConfig  {
    @Value("${github.baseUrl}")
    private String githubApiBaseUrl;

    @Bean
    public RestClient githubRestClient() {
        return RestClient.builder()
                .baseUrl(githubApiBaseUrl)
                .build();
    }

}
