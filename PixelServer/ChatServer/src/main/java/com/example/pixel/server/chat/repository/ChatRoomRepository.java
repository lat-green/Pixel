package com.example.pixel.server.chat.repository;

import com.example.pixel.server.chat.model.ChatRoom;
import com.example.pixel.server.chat.model.ChatUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, String> {

    Optional<ChatRoom> findBySenderAndRecipient(ChatUser sender, ChatUser recipient);

}
