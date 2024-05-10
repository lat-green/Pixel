package com.example.pixel.server.chat.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ArticlesController {

    @GetMapping("/articles")
    public String[] getArticles(Authentication authentication) {
        System.out.println(authentication);
        return new String[]{"Article 1", "Article 2", "Article 3"};
    }

}