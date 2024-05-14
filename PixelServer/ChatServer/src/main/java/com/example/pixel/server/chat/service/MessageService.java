package com.example.pixel.server.chat.service;

import com.example.pixel.server.chat.dto.message.ChatTextMessageCreateRequest;
import com.example.pixel.server.chat.entity.ChatUser;
import com.example.pixel.server.chat.entity.message.ChatMessage;
import com.example.pixel.server.chat.entity.message.ChatTextMessage;
import com.example.pixel.server.chat.exception.MessageNotFoundException;
import com.example.pixel.server.chat.repository.MessageRepository;
import com.example.pixel.server.chat.repository.RoomRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;

@AllArgsConstructor
@Component
public class MessageService {

    private MessageRepository repository;
    private RoomRepository roomRepository;

    public Collection<ChatMessage> getAllMessages() {
        return repository.findAll();
    }

    public ChatMessage getOneMessage(long id) {
        return repository.findById(id).orElseThrow(() -> new MessageNotFoundException(id));
    }

    public ChatMessage createTextMessage(
            long roomId,
            ChatUser user,
            ChatTextMessageCreateRequest request
    ) {
        var message = new ChatTextMessage();
        message.setRoom(roomRepository.getReferenceById(roomId));
        message.setUser(user);
        message.setContent(request.getContent());
        return repository.save(message);
    }

}
