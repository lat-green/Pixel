package com.example.pixel.server.util.controller.advice.exception;

public class UserAlreadyExists extends RuntimeException {

    public UserAlreadyExists(Long userId, String username) {
        super("id = " + userId + " username = " + username);
    }

}
