package com.example.pixel.server.chat.service;

import com.example.pixel.server.chat.exception.ResourceNotFoundException;
import com.example.pixel.server.chat.model.ChatMessage;
import com.example.pixel.server.chat.model.ChatRoom;
import com.example.pixel.server.chat.model.MessageStatus;
import com.example.pixel.server.chat.repository.ChatMessageRepository;
import com.example.pixel.server.chat.repository.ChatUserRepository;
import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ChatMessageService {

    private final ChatMessageRepository repository;
    private final ChatUserRepository userRepository;
    private final ChatRoomService chatRoomService;

    public ChatMessage save(ChatMessage chatMessage) {
        chatMessage.setStatus(MessageStatus.RECEIVED);
        repository.save(chatMessage);
        return chatMessage;
    }

    public long countNewMessages(Long senderId, Long recipientId) {
        val sender = userRepository.getReferenceById(senderId);
        val recipient = userRepository.getReferenceById(recipientId);
        return chatRoomService.getChatRoom(senderId, recipientId, false)
                .get()
                .getMessages().stream().filter(m -> m.getStatus() == MessageStatus.RECEIVED).count();
    }

    public List<ChatMessage> findChatMessages(Long senderId, Long recipientId) {
        var chatId = chatRoomService.getChatRoom(senderId, recipientId, false);
        var messages = chatId.map(ChatRoom::getMessages).orElse(new ArrayList<>());
        if (!messages.isEmpty()) {
            updateStatuses(senderId, recipientId, MessageStatus.DELIVERED);
        }
        return messages;
    }

    @Transactional
    public void updateStatuses(Long senderId, Long recipientId, MessageStatus status) {
        chatRoomService.getChatRoom(senderId, recipientId, false).ifPresent(room -> room.getMessages().forEach(chatMessage -> {
            chatMessage.setStatus(status);
            repository.save(chatMessage);
        }));
    }

    public ChatMessage findById(String id) {
        return repository
                .findById(id)
                .map(chatMessage -> {
                    chatMessage.setStatus(MessageStatus.DELIVERED);
                    return repository.save(chatMessage);
                })
                .orElseThrow(() ->
                        new ResourceNotFoundException("can't find message (" + id + ")"));
    }

}
