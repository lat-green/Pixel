package com.example.pixel.server.authorization.exception;

import com.example.pixel.server.util.controller.advice.exception.NotFoundException;

public class ClientNotFoundException extends NotFoundException {

    public ClientNotFoundException(Long id) {
        super("Could not find client with id = " + id);
    }

    public ClientNotFoundException(String clientId) {
        super("Could not find client with clientId = " + clientId);
    }

}
