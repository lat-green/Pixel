package com.example.pixel.server.chat.repository;

import com.example.pixel.server.chat.entity.ChatUser;
import com.example.pixel.server.chat.entity.message.ChatTextMessage;
import com.example.pixel.server.chat.entity.room.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TextMessageRepository extends JpaRepository<ChatTextMessage, Long> {

    Optional<ChatTextMessage> findByRoomAndUserAndContent(ChatRoom room, ChatUser user, String content);

}
