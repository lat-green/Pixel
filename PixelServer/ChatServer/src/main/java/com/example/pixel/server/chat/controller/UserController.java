package com.example.pixel.server.chat.controller;

import com.example.pixel.server.chat.entity.ChatUser;
import com.example.pixel.server.chat.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public ChatUser getMe(ChatUser user) {
        return user;
    }

    @GetMapping("/{id}")
    public ChatUser getOneUser(@PathVariable long id) {
        return userService.getOneUser(id);
    }

    @GetMapping("")
    public Collection<ChatUser> getAllUsers() {
        return userService.getAllUsers();
    }

}