package com.example.pixel.server.chat.controller.advice;

import com.example.pixel.server.chat.controller.advice.exception.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@RestControllerAdvice
public class AllExceptionControllerAdvice {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @ExceptionHandler({TokenRefreshException.class, AccessDeniedException.class})
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public Object handleTokenRefreshException(RuntimeException ex, WebRequest request) {
        return newException(ex, request);
    }

    private Object newException(RuntimeException e, WebRequest request) {
        final var m = e.getMessage();
        if (m == null)
            return new ExceptionDTO(e.getClass().getName(), request.getDescription(false));
        return new InfoExceptionDTO(e.getClass().getName(), m, request.getDescription(false));
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Object processRuntimeException(RuntimeException e, WebRequest request) {
        log.error("Unsupported Exception:", e);
        return newException(e, request);
    }

    @ExceptionHandler({NotHaveRefreshTokenException.class, NotHaveTokenException.class, BadCredentialsException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Object processNotHaveRefreshToken(RuntimeException e, WebRequest request) {
        return newException(e, request);
    }

    @ResponseBody
    @ExceptionHandler({UserNotFoundException.class, ArticleNotFoundException.class, CommentNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Object notFoundException(RuntimeException e, WebRequest request) {
        return newException(e, request);
    }

    @ResponseBody
    @ExceptionHandler({UserAlreadyExists.class, UserAlreadySubscribe.class, UserNotSubscribe.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Object clientException(RuntimeException e, WebRequest request) {
        return newException(e, request);
    }

    @ResponseBody
    @ExceptionHandler({ForbiddenException.class})
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public Object forbiddenException(RuntimeException e, WebRequest request) {
        return newException(e, request);
    }

    @ResponseBody
    @ExceptionHandler({NotValidJWT.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Object unauthorizedException(RuntimeException e, WebRequest request) {
        return newException(e, request);
    }

    @AllArgsConstructor
    @Builder
    @Data
    private static class ExceptionDTO {

        final Date date = new Date();
        String type;
        String description;

    }

    @AllArgsConstructor
    @Builder
    @Data
    private static class InfoExceptionDTO {

        final Date date = new Date();
        String type;
        String info;
        String description;

    }

}
