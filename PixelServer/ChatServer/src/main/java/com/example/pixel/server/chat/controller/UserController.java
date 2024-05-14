package com.example.pixel.server.chat.controller;

import com.example.pixel.server.chat.entity.ChatUser;
import com.example.pixel.server.chat.serializer.ChatUserAttachmentToUserSerializer;
import com.example.pixel.server.chat.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    private final ObjectMapper objectMapper;

    public UserController(UserService userService, ChatUserAttachmentToUserSerializer serializer) {
        this.userService = userService;
        this.objectMapper = new ObjectMapper().registerModule(new SimpleModule().addSerializer(serializer));
    }

    @GetMapping("/me")
    public ChatUser getMe(ChatUser user) {
        return user;
    }

    @GetMapping("/me/chats")
    public String getMeChats(ChatUser user) throws JsonProcessingException {
        return objectMapper.writeValueAsString(user.getChats());
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