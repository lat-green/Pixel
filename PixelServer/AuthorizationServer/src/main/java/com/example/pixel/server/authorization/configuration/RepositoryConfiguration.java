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
        final var readScope = createScope("chat.read");
        final var writeScope = createScope("chat.write");
        final var profileReadScope = createScope("profile.read");
        final var profileWriteScope = createScope("profile.write");
        final var openidScope = createScope("openid");
        final var arseny = createUser("Arseny");
        createUser("Ангелина");
        createUser("Света");
        createUser("Никита");
        createUser("Татьяна Василевна");
        final var testClient = clientRepository.findByClientId("test-client").orElseGet(() -> {
            var m = new AuthClient();
            m.setOwner(arseny);
            m.setClientName("Test Client");
            m.setClientId("test-client");
            m.setClientSecret(passwordEncoder.encode("test-client"));
            m.setScopes(Set.of(readScope, writeScope, profileReadScope, profileWriteScope, openidScope));
            m.setRedirectUris(Set.of(
                    AuthRedirectUri.builder().name("https://oauth.pstmn.io/v1/browser-callback").client(m).build(),
                    AuthRedirectUri.builder().name("http://localhost:3000/auth/code").client(m).build()
            ));
            return clientRepository.save(m);
        });
    }

    private AuthClientScope createScope(String name) {
        return clientScopeRepository.findByName(name).orElseGet(() -> {
            var m = new AuthClientScope();
            m.setName(name);
            return clientScopeRepository.save(m);
        });
    }

    private AuthUser createUser(String name) {
        return userRepository.findByUsername(name).orElseGet(() -> {
            var m = new AuthUser();
            m.setUsername(name);
            m.setPassword(passwordEncoder.encode("1234"));
            return userRepository.save(m);
        });
    }

}
