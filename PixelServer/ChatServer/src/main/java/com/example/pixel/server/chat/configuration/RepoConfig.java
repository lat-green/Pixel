package com.example.pixel.server.chat.configuration;

import com.example.pixel.server.chat.repository.ChatMessageRepository;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;

@AllArgsConstructor
@Configuration
public class RepoConfig {

    private final ChatMessageRepository chatMessageRepository;

    @PostConstruct
    void init() {
        chatMessageRepository.deleteAll();
    }

}
