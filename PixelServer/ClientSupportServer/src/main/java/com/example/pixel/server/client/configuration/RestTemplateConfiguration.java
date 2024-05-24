package com.example.pixel.server.client.configuration;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@AllArgsConstructor
@Configuration
public class RestTemplateConfiguration {

    @Bean
    RestTemplate newRestTemplate() {
        return new RestTemplate();
    }

}
