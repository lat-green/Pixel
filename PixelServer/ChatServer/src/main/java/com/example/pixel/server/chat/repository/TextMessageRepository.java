package com.example.pixel.server.chat.repository;

import com.example.pixel.server.chat.entity.Customer;
import com.example.pixel.server.chat.entity.chat.Chat;
import com.example.pixel.server.chat.entity.message.TextMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TextMessageRepository extends JpaRepository<TextMessage, Long> {

    Optional<TextMessage> findByChatAndUserAndContent(Chat chat, Customer user, String content);

}
