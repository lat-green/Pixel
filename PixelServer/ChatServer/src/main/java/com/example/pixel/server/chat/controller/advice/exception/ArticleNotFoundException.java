package com.example.pixel.server.chat.controller.advice.exception;

public class ArticleNotFoundException extends RuntimeException {

    public ArticleNotFoundException(Long id) {
        super("Could not find article with id = " + id);
    }

    public ArticleNotFoundException(String title) {
        super("Could not find article with title = " + title);
    }

}
