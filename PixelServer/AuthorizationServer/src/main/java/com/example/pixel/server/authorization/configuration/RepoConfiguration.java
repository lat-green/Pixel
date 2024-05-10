package com.example.pixel.server.authorization.configuration;

import com.example.pixel.server.authorization.entity.User;
import com.example.pixel.server.authorization.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Configuration
public class RepoConfiguration {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Transactional
    @EventListener(ApplicationReadyEvent.class)
    public void init() {
//        userRepository.deleteAll();
        final var maksim = userRepository.findByUsername("Maksim").orElseGet(() -> {
            var user = new User();
            user.setUsername("Maksim");
            user.setPassword(passwordEncoder.encode("1234"));
            return userRepository.save(user);
        });
        final var arseny = userRepository.findByUsername("Arseny").orElseGet(() -> {
            var user = new User();
            user.setUsername("Arseny");
            user.setPassword(passwordEncoder.encode("1234"));
            return userRepository.save(user);
        });
    }

}
