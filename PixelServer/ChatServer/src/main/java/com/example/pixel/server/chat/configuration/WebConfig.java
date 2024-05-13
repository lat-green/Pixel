package com.example.pixel.server.chat.configuration;

import com.example.pixel.server.chat.security.HeaderVersionArgumentResolver;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@AllArgsConstructor
@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final HeaderVersionArgumentResolver resolver;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(resolver);
    }

}