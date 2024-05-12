package com.example.pixel.server.chat.controller;

import com.example.pixel.server.chat.entity.ChatUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ArticlesController {

    @GetMapping("/articles")
    public String[] getArticles(ChatUser user) {
        System.out.println(user);
        return new String[]{"Article 1", "Article 2", "Article 3"};
    }

}