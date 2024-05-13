package com.example.pixel.server.chat.configuration;

import com.example.pixel.server.chat.entity.ChatTextMessage;
import com.example.pixel.server.chat.repository.MessageRepository;
import com.example.pixel.server.chat.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Configuration
public class RepositoryConfiguration {

    private final UserRepository userRepository;
    private final MessageRepository messageRepository;

    private final ObjectMapper objectMapper;

    @Transactional
    @EventListener(ApplicationReadyEvent.class)
    public void init() {
        var arseny = userRepository.findByUsername("Arseny").orElseThrow();
        messageRepository.deleteAll();
        var m1 = new ChatTextMessage();
        m1.setContent("Message 1");
        messageRepository.save(m1);
    }

}
