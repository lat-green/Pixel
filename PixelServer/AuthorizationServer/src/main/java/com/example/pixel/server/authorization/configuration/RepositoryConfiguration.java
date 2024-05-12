package com.example.pixel.server.authorization.configuration;

import com.example.pixel.server.authorization.entity.AuthUser;
import com.example.pixel.server.authorization.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Configuration
public class RepositoryConfiguration {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final ObjectMapper objectMapper;

    @Transactional
    @EventListener(ApplicationReadyEvent.class)
    public void init() {
        final var maksim = userRepository.findByUsername("Maksim").orElseGet(() -> {
            var m = new AuthUser();
            m.setUsername("Maksim");
            m.setPassword(passwordEncoder.encode("1234"));
            return userRepository.save(m);
        });
        final var arseny = userRepository.findByUsername("Arseny").orElseGet(() -> {
            var m = new AuthUser();
            m.setUsername("Arseny");
            m.setPassword(passwordEncoder.encode("1234"));
            return userRepository.save(m);
        });
    }

}
