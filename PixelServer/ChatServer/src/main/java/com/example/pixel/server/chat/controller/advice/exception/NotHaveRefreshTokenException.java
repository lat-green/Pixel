package com.example.pixel.server.chat.controller.advice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class NotHaveRefreshTokenException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public NotHaveRefreshTokenException() {
        super("failed found refresh token in cookies");
    }

}
