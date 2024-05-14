package com.example.pixel.server.chat.service;

import com.example.pixel.server.chat.entity.message.ChatMessage;
import com.example.pixel.server.chat.exception.MessageNotFoundException;
import com.example.pixel.server.chat.repository.MessageRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;

@AllArgsConstructor
@Component
public class MessageService {

    private MessageRepository repository;

    public Collection<ChatMessage> getAllMessages() {
        return repository.findAll();
    }

    public ChatMessage getOneMessage(long id) {
        return repository.findById(id).orElseThrow(() -> new MessageNotFoundException(id));
    }

}
