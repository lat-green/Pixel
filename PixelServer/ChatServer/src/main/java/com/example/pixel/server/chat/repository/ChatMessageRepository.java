package com.example.pixel.server.chat.repository;

import com.example.pixel.server.chat.model.ChatMessage;
import com.example.pixel.server.chat.model.ChatUser;
import com.example.pixel.server.chat.model.MessageStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, String> {

}