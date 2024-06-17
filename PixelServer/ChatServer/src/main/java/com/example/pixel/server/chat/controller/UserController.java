package com.example.pixel.server.chat.controller;

import com.example.pixel.server.chat.controller.securiry.HasScopeProfileRead;
import com.example.pixel.server.chat.entity.Customer;
import com.example.pixel.server.chat.entity.attachment.ChatAttachment;
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
import java.util.List;

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
        return objectMapper.writeValueAsString(user.getChats().stream().map(attachment -> attachment.getChat().getId()).toList());
    }

    @HasScopeProfileRead
    @GetMapping("/me/attachments")
    public List<ChatAttachment> getMeAttachments(Customer user) throws JsonProcessingException {
        return user.getChats();
    }

    @GetMapping("/{id}")
    public Customer getOneUser(@PathVariable long id) {
        return userService.getOneUser(id);
    }

    @GetMapping("")
    public Collection<Customer> getAllUsers() {
        return userService.getAllUsers();
    }

}