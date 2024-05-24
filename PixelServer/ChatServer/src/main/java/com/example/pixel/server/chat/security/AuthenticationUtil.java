package com.example.pixel.server.chat.security;

import com.example.pixel.server.util.controller.advice.exception.ForbiddenException;
import com.example.pixel.server.util.controller.advice.exception.NotHaveTokenException;
import org.springframework.security.core.Authentication;

public class AuthenticationUtil {

    public static Object authenticated(Authentication authentication) {
        if (authentication == null)
            throw new NotHaveTokenException();
        if (!authentication.isAuthenticated())
            throw new ForbiddenException();
        return authentication.getPrincipal();
    }

}
