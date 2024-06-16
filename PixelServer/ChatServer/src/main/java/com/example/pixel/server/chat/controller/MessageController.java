package com.example.pixel.server.chat.controller;

import com.example.pixel.server.chat.dto.message.TextMessageReplaceRequest;
import com.example.pixel.server.chat.entity.Customer;
import com.example.pixel.server.chat.entity.message.Message;
import com.example.pixel.server.chat.entity.message.TextMessage;
import com.example.pixel.server.chat.service.MessageService;
import com.example.pixel.server.util.controller.advice.exception.ForbiddenException;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/messages")
@AllArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @GetMapping("/{id}")
    public Message getOneMessage(@PathVariable long id, Customer user) {
        var message = messageService.getOneMessage(id);
        if (!message.getChat().getUserRole(user).canRead)
            throw new ForbiddenException("can not get message with id = " + id + " because it's not your chat");
        return message;
    }

    @DeleteMapping("/{id}")
    public void deleteOneMessage(@PathVariable long id, Customer user) {
        var message = messageService.getOneMessage(id);
        if (message.getUser().getId() != user.getId())
            throw new ForbiddenException("can not delete message with id = " + id + " because it's not your message");
        messageService.deleteOneMessage(id);
    }

    @PutMapping("/{id}/text")
    public TextMessage replaceTextMessage(@PathVariable long id, Customer user, @RequestBody TextMessageReplaceRequest replaceRequest) {
        var message = messageService.getOneMessage(id);
        if (message.getUser().getId() != user.getId())
            throw new ForbiddenException("can not replace message with id = " + id + " because it's not your message");
        return messageService.replaceTextMessage(id, replaceRequest);
    }

}