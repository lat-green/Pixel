package com.example.pixel.server.chat.controller;

import com.example.pixel.server.chat.entity.ChatMessage;
import com.example.pixel.server.chat.service.MessageService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/messages")
@AllArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @GetMapping("/{id}")
    public ChatMessage getOneMessage(@PathVariable long id) {
        return messageService.getOneMessage(id);
    }

    @GetMapping("")
    public Collection<ChatMessage> getAllMessages() {
        return messageService.getAllMessages();
    }

}