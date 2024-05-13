package com.example.pixel.server.chat.exception;

import com.example.pixel.server.util.controller.advice.exception.NotFoundException;

public class MessageNotFoundException extends NotFoundException {

    public MessageNotFoundException(Long id) {
        super("Could not find message with id = " + id);
    }

}
