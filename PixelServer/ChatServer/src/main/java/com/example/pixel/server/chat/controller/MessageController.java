package com.example.pixel.server.chat.controller;

import com.example.pixel.server.chat.entity.ChatUser;
import com.example.pixel.server.chat.entity.message.ChatMessage;
import com.example.pixel.server.chat.service.MessageService;
import com.example.pixel.server.util.controller.advice.exception.ForbiddenException;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/messages")
@AllArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @GetMapping("/{id}")
    public ChatMessage getOneMessage(@PathVariable long id, ChatUser user) {
        var message = messageService.getOneMessage(id);
        if (!message.getRoom().getUserRole(user).canRead)
            throw new ForbiddenException("can not get message with id = " + id + " because it's not your chat");
        return message;
    }

}