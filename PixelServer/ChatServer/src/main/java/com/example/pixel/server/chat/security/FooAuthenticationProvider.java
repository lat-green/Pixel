package com.example.pixel.server.chat.security;

import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

@AllArgsConstructor
public class FooAuthenticationProvider implements AuthenticationProvider {

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        System.out.println("token: " + authentication);
        return authentication;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }

}
