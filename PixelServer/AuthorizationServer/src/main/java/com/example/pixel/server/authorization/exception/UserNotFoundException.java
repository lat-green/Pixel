package com.example.pixel.server.authorization.exception;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserNotFoundException extends UsernameNotFoundException {

    public UserNotFoundException(Long id) {
        super("Could not find user with id = " + id);
    }

    public UserNotFoundException(String username) {
        super("Could not find user with username = " + username);
    }

}
