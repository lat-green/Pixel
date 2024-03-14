package com.example.pixel.server.authorization.controller;

import com.example.pixel.server.authorization.entity.User;
import com.example.pixel.server.authorization.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService service;

    @GetMapping("/users/{id}")
    public User getOne(@PathVariable long id) {
        return service.getOneUser(id);
    }

    @GetMapping("/users/me")
    public User getMe(@AuthenticationPrincipal User user) {
        return user;
    }

}
