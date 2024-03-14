package com.example.pixel.server.authorization.security;

import com.example.pixel.server.authorization.dto.AuthenticatedUser;
import com.example.pixel.server.authorization.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@AllArgsConstructor
@Component
public class UserServiceAuthenticationProvider implements AuthenticationProvider {

    private final UserService service;
    private final PasswordEncoder encoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        var username = authentication.getPrincipal().toString();
        var password = authentication.getCredentials().toString();
        var user = service.loadUserByUsername(username);
        if (!encoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("Bad credentials");
        }
        if (!user.isEnabled()) {
            throw new DisabledException("User account is not active");
        }
        return new AuthenticatedUser(user, Set.of());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

}
