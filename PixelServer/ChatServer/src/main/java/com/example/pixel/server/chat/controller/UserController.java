package com.example.pixel.server.chat.controller;

import com.example.pixel.server.chat.controller.securiry.HasScopeProfileRead;
import com.example.pixel.server.chat.controller.securiry.HasScopeProfileWrite;
import com.example.pixel.server.chat.dto.user.CustomerReplaceRequest;
import com.example.pixel.server.chat.entity.Customer;
import com.example.pixel.server.chat.serializer.ChatUserAttachmentToUserSerializer;
import com.example.pixel.server.chat.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.web.bind.annotation.*;

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

    @HasScopeProfileRead
    @GetMapping("/me")
    public Customer getMe(Customer user) {
        return user;
    }

    @HasScopeProfileRead
    @GetMapping("/me/chats")
    public String getMeChats(Customer user) throws JsonProcessingException {
        return objectMapper.writeValueAsString(user.getChats().stream().map(x -> x.getChatRoom().getId()).toList());
    }

    @GetMapping("/{id}")
    public Customer getOneUser(@PathVariable long id) {
        return userService.getOneUser(id);
    }

    @GetMapping("")
    public Collection<Customer> getAllUsers() {
        return userService.getAllUsers();
    }

    @HasScopeProfileWrite
    @PutMapping("")
    public Customer replaceUser(Customer user, @RequestBody CustomerReplaceRequest replaceRequest) throws JsonProcessingException {
        return userService.replaceUser(user, replaceRequest);
    }

}