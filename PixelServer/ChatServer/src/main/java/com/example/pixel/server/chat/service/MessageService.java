package com.example.pixel.server.chat.service;

import com.example.pixel.server.chat.dto.message.TextMessageCreateRequest;
import com.example.pixel.server.chat.entity.Customer;
import com.example.pixel.server.chat.entity.message.Message;
import com.example.pixel.server.chat.entity.message.TextMessage;
import com.example.pixel.server.chat.exception.MessageNotFoundException;
import com.example.pixel.server.chat.repository.MessageRepository;
import com.example.pixel.server.chat.repository.RoomRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@AllArgsConstructor
@Component
public class MessageService {

    private MessageRepository repository;
    private RoomRepository roomRepository;

    public Collection<Message> getAllMessages() {
        return repository.findAll();
    }

    public Message getOneMessage(long id) {
        return repository.findById(id).orElseThrow(() -> new MessageNotFoundException(id));
    }

    public Message createTextMessage(
            long roomId,
            Customer user,
            TextMessageCreateRequest request
    ) {
        var message = new TextMessage();
        message.setChat(roomRepository.getReferenceById(roomId));
        message.setUser(user);
        message.setContent(request.getContent());
        return repository.save(message);
    }

    public List<Message> getAllMessagesOfRoom(long roomId) {
        var room = roomRepository.getReferenceById(roomId);
        return repository.findAllByChat(room);
    }

    public void deleteOneMessage(long id) {
        repository.deleteById(id);
    }

}
