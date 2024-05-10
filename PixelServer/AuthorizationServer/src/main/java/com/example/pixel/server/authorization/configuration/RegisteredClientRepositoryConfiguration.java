package com.example.pixel.server.authorization.configuration;

import com.example.pixel.server.util.configuration.ServerAddress;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;

@AllArgsConstructor
@Configuration
public class RegisteredClientRepositoryConfiguration {

    private final PasswordEncoder passwordEncoder;

    @Bean
    public RegisteredClientRepository registeredClientRepository(
            ServerAddress address
    ) {
        return new InMemoryRegisteredClientRepository(
                RegisteredClient
                        .withId("test-client-id")
                        .clientName("Test Client")
                        .clientId("test-client")
                        .clientSecret(passwordEncoder.encode("test-client"))
                        .scope("articles.read")
                        .scope("openid")
                        .redirectUri("https://oauth.pstmn.io/v1/browser-callback")
                        .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                        .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                        .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                        .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                        .build()
        );
    }

}
