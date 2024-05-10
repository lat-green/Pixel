package com.example.pixel.server.chat.controller.advice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class NotHaveTokenException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public NotHaveTokenException() {
        super("failed found token");
    }

}
