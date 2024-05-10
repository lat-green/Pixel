package com.example.pixel.server.chat;

import com.example.pixel.server.chat.controller.advice.exception.ForbiddenException;
import com.example.pixel.server.chat.controller.advice.exception.NotHaveTokenException;
import com.example.pixel.server.chat.model.ChatUser;
import org.springframework.security.core.Authentication;

public class Util {

    public static ChatUser authenticated(Authentication authentication) {
        if (authentication == null)
            throw new NotHaveTokenException();
        if (!authentication.isAuthenticated())
            throw new ForbiddenException();
        return (ChatUser) authentication.getPrincipal();
    }

}
