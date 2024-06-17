package com.example.pixel.server.chat.repository;

import com.example.pixel.server.chat.entity.chat.Chat;
import com.example.pixel.server.chat.entity.message.SystemMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SystemMessageRepository extends JpaRepository<SystemMessage, Long> {

    List<SystemMessage> findAllByChatAndContent(Chat chat, String content);

}
