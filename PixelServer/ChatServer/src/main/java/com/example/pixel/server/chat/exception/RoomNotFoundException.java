package com.example.pixel.server.chat.exception;

import com.example.pixel.server.util.controller.advice.exception.NotFoundException;

public class RoomNotFoundException extends NotFoundException {

    public RoomNotFoundException(Long id) {
        super("Could not find room with id = " + id);
    }

}
