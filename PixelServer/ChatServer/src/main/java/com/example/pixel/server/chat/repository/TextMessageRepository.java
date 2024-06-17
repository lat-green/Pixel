package com.example.pixel.server.chat.repository;

import com.example.pixel.server.chat.entity.Customer;
import com.example.pixel.server.chat.entity.chat.Chat;
import com.example.pixel.server.chat.entity.message.TextUserMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TextMessageRepository extends JpaRepository<TextUserMessage, Long> {

    Optional<TextUserMessage> findByChatAndUserAndContent(Chat chat, Customer user, String content);

}
