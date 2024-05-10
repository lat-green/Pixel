package com.example.pixel.server.chat.service;

import com.example.pixel.server.chat.model.ChatRoom;
import com.example.pixel.server.chat.repository.ChatRoomRepository;
import com.example.pixel.server.chat.repository.ChatUserRepository;
import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class ChatRoomService {

    private final ChatUserRepository userRepository;
    private final ChatRoomRepository repository;

    public Optional<ChatRoom> getChatRoom(Long senderId, Long recipientId, boolean createIfNotExist) {
        val sender = userRepository.getReferenceById(senderId);
        val recipient = userRepository.getReferenceById(recipientId);
        val room = repository.findBySenderAndRecipient(sender, recipient);
        if (createIfNotExist)
            return Optional.of(room.orElseGet(() -> repository.save(ChatRoom.builder()
                    .sender(sender)
                    .recipient(recipient)
                    .build())));
        return room;
    }

}
