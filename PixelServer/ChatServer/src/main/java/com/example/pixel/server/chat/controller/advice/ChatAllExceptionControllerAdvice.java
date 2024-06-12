package com.example.pixel.server.chat.controller.advice;

import com.example.pixel.server.util.controller.advice.InfoExceptionDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import static com.example.pixel.server.util.controller.advice.ExceptionUtil.newException;

@RestControllerAdvice
public class ChatAllExceptionControllerAdvice {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @ResponseBody
    @ExceptionHandler({AccessDeniedException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Object processNotHaveRefreshToken(RuntimeException e, WebRequest request) {
        return newException(e, request);
    }

}
