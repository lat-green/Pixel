package com.example.pixel.server.util.controller.advice;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

@AllArgsConstructor
public class FilterChainExceptionHandler extends OncePerRequestFilter {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final HandlerExceptionResolver resolver;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
        try {
            filterChain.doFilter(request, response);
        } catch (Exception e) {
//            log.error("Spring Security Filter Chain Exception:", e);
            resolver.resolveException(request, response, null, e);
        }
    }

}