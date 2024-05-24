package com.example.pixel.server.chat.controller.securiry;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("hasAnyAuthority('SCOPE_chat.read', 'SCOPE_chat.write')")
public @interface HasScopeRead {

}
