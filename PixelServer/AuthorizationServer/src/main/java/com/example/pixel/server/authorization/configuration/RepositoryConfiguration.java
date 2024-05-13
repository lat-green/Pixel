package com.example.pixel.server.authorization.configuration;

import com.example.pixel.server.authorization.entity.AuthClient;
import com.example.pixel.server.authorization.entity.AuthClientScope;
import com.example.pixel.server.authorization.entity.AuthRedirectUri;
import com.example.pixel.server.authorization.entity.AuthUser;
import com.example.pixel.server.authorization.repository.AuthRedirectUriRepository;
import com.example.pixel.server.authorization.repository.ClientRepository;
import com.example.pixel.server.authorization.repository.ClientScopeRepository;
import com.example.pixel.server.authorization.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@AllArgsConstructor
@Configuration
public class RepositoryConfiguration {

    private final UserRepository userRepository;
    private final ClientRepository clientRepository;
    private final ClientScopeRepository clientScopeRepository;
    private final AuthRedirectUriRepository redirectUriRepository;

    private final PasswordEncoder passwordEncoder;

    private final ObjectMapper objectMapper;

    @Transactional
    @EventListener(ApplicationReadyEvent.class)
    public void init() {
        final var testScope = clientScopeRepository.findByName("test").orElseGet(() -> {
            var m = new AuthClientScope();
            m.setName("test");
            return clientScopeRepository.save(m);
        });
        final var testClient = clientRepository.findByClientId("test-client").orElseGet(() -> {
            var m = new AuthClient();
            m.setClientName("Test Client");
            m.setClientId("test-client");
            m.setClientSecret(passwordEncoder.encode("test-client"));
            m.setScopes(Set.of(testScope));
            var postmanCallback = new AuthRedirectUri();
            postmanCallback.setName("https://oauth.pstmn.io/v1/browser-callback");
            postmanCallback.setClient(m);
            m.setRedirectUris(Set.of(postmanCallback));
            return clientRepository.save(m);
        });
        final var arseny = userRepository.findByUsername("Arseny").orElseGet(() -> {
            var m = new AuthUser();
            m.setUsername("Arseny");
            m.setPassword(passwordEncoder.encode("1234"));
            return userRepository.save(m);
        });
    }

}
