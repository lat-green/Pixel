package com.example.pixel.server.authorization.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthenticationToken;
import org.springframework.security.oauth2.server.resource.web.BearerTokenResolver;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@AllArgsConstructor
@Slf4j
public class BearerOAuth2TokenAuthenticationFilter extends OncePerRequestFilter {

    private final BearerTokenResolver bearerTokenResolver;
    private final AuthenticationManager authenticationManager;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain fc) throws ServletException, IOException {
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            final String token = bearerTokenResolver.resolve(request);
            if (token != null)
                SecurityContextHolder.getContext().setAuthentication(authenticationManager.authenticate(new BearerTokenAuthenticationToken(token)));
        }
        fc.doFilter(request, response);
    }

}