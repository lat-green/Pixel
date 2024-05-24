package com.example.pixel.server.util.controller.advice;

import org.springframework.web.context.request.WebRequest;

public class ExceptionUtil {

    public static Object newException(RuntimeException e, WebRequest request) {
        final var m = e.getMessage();
        if (m == null)
            return new ExceptionDTO(e.getClass().getName(), request.getDescription(false));
        return new InfoExceptionDTO(e.getClass().getName(), m, request.getDescription(false));
    }

}
