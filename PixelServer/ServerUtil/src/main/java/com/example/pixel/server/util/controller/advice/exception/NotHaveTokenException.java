package com.example.pixel.server.util.controller.advice.exception;

public class NotHaveTokenException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public NotHaveTokenException() {
        super("failed found token");
    }

}
