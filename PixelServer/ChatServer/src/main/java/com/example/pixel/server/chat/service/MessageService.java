package com.example.pixel.server.chat.service;

import com.example.pixel.server.chat.dto.message.ImageMessageCreateRequest;
import com.example.pixel.server.chat.dto.message.MessageCreateNotification;
import com.example.pixel.server.chat.dto.message.TextMessageCreateRequest;
import com.example.pixel.server.chat.entity.Customer;
import com.example.pixel.server.chat.entity.message.ImageMessage;
import com.example.pixel.server.chat.entity.message.Message;
import com.example.pixel.server.chat.entity.message.TextMessage;
import com.example.pixel.server.chat.exception.MessageNotFoundException;
import com.example.pixel.server.chat.repository.MessageRepository;
import com.example.pixel.server.chat.repository.RoomRepository;
import lombok.AllArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@AllArgsConstructor
@Component
public class MessageService {

    private final MessageRepository repository;
    private final RoomRepository roomRepository;
    private final SimpMessagingTemplate messagingTemplate;

    public Collection<Message> getAllMessages() {
        return repository.findAll();
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
        message = repository.save(message);
        messagingTemplate.convertAndSendToUser(
                "" + roomId,
                "/create",
                new MessageCreateNotification(message.getId())
        );
        return message;
    }

    public Message createImageMessage(long roomId, Customer user, ImageMessageCreateRequest request) {
        var message = new ImageMessage();
        message.setChat(roomRepository.getReferenceById(roomId));
        message.setUser(user);
        message.setUrl(request.getUrl());
        message = repository.save(message);
        messagingTemplate.convertAndSendToUser(
                "" + roomId,
                "/create",
                new MessageCreateNotification(message.getId())
        );
        return message;
    }

    public List<Message> getAllMessagesOfRoom(long roomId) {
        var room = roomRepository.getReferenceById(roomId);
        return repository.findAllByChat(room);
    }

    public void deleteOneMessage(long id) {
        var message = getOneMessage(id);
        repository.deleteById(id);
        messagingTemplate.convertAndSendToUser(
                "" + message.getChat().getId(),
                "/delete",
                new MessageCreateNotification(
                        message.getId()
                )
        );
    }

    public Message getOneMessage(long id) {
        return repository.findById(id).orElseThrow(() -> new MessageNotFoundException(id));
    }

}
