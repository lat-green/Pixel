package com.example.pixel.server.chat.repository;

import com.example.pixel.server.chat.entity.chat.Chat;
import com.example.pixel.server.chat.entity.message.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> findAllByChat(Chat chat);

}
