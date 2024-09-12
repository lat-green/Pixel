package com.example.pixel.server.chat.service;

import com.example.pixel.server.chat.dto.message.ImageMessageCreateRequest;
import com.example.pixel.server.chat.dto.message.MessageCreateNotification;
import com.example.pixel.server.chat.dto.message.TextMessageCreateRequest;
import com.example.pixel.server.chat.dto.message.TextMessageReplaceRequest;
import com.example.pixel.server.chat.entity.Customer;
import com.example.pixel.server.chat.entity.message.ImageUserMessage;
import com.example.pixel.server.chat.entity.message.Message;
import com.example.pixel.server.chat.entity.message.TextUserMessage;
import com.example.pixel.server.chat.entity.message.UserMessage;
import com.example.pixel.server.chat.exception.MessageNotFoundException;
import com.example.pixel.server.chat.repository.AttachmentRepository;
import com.example.pixel.server.chat.repository.MessageRepository;
import com.example.pixel.server.chat.repository.RoomRepository;
import lombok.AllArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@AllArgsConstructor
@Component
public class MessageService {

    private final MessageRepository repository;
    private final RoomRepository roomRepository;
    private final AttachmentRepository attachmentRepository;
    private final RoomService roomService;
    private final SimpMessagingTemplate messagingTemplate;

    public List<Message> getAllMessages() {
        return repository.findAll();
    }

    public UserMessage createTextMessage(
            long roomId,
            Customer user,
            TextMessageCreateRequest request
    ) {
        var message = new TextUserMessage();
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

    public UserMessage createImageMessage(long roomId, Customer user, ImageMessageCreateRequest request) {
        var message = new ImageUserMessage();
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

    public List<? extends Message> getAllMessagesOfRoom(long roomId, Customer user) {
        var chat = roomService.getOneRoom(roomId);
        var attachment = chat.getAttachment(user);
        attachment.updateLastRead();
        attachmentRepository.save(attachment);
        return repository.findAllByChat(chat);
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

    public TextUserMessage replaceTextMessage(long id, TextMessageReplaceRequest replaceRequest) {
        var message = (TextUserMessage) getOneMessage(id);
        var name = replaceRequest.getContent();
        if (name != null)
            message.setContent(name);
        message = repository.save(message);
        messagingTemplate.convertAndSendToUser(
                "" + message.getChat().getId(),
                "/edit",
                new MessageCreateNotification(
                        message.getId()
                )
        );
        return message;
    }

}
