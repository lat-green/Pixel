package com.example.pixel.server.chat.service;

import com.example.pixel.server.chat.entity.ChatMessage;
import com.example.pixel.server.chat.entity.MessageStatus;
import com.example.pixel.server.chat.repository.ChatMessageRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class ChatMessageService {

    private final ChatMessageRepository repository;
    private final ChatRoomService chatRoomService;

    public ChatMessage save(ChatMessage chatMessage) {
        chatMessage.setStatus(MessageStatus.RECEIVED);
        repository.save(chatMessage);
        return chatMessage;
    }

    public long countNewMessages(String senderId, String recipientId) {
        return repository.countBySenderIdAndRecipientIdAndStatus(
                senderId, recipientId, MessageStatus.RECEIVED);
    }

    public List<ChatMessage> findChatMessages(String senderId, String recipientId) {
        var chatId = chatRoomService.getChatId(senderId, recipientId, false);
        var messages =
                chatId.map(cId -> repository.findByChatId(cId)).orElse(new ArrayList<>());
        if (messages.size() > 0) {
            updateStatuses(senderId, recipientId, MessageStatus.DELIVERED);
        }
        return messages;
    }

    public void updateStatuses(String senderId, String recipientId, MessageStatus status) {
        repository.findBySenderIdAndRecipientId(senderId, recipientId).ifPresent(x -> {
            x.setStatus(status);
            repository.save(x);
        });
    }

    public ChatMessage findById(Long id) {
        return repository
                .findById(id)
                .map(chatMessage -> {
                    chatMessage.setStatus(MessageStatus.DELIVERED);
                    return repository.save(chatMessage);
                }).orElseThrow();
    }

}