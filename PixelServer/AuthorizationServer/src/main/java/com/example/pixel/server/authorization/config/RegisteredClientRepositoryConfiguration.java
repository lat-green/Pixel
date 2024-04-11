package com.example.pixel.server.authorization.config;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

@AllArgsConstructor
@Configuration
public class RegisteredClientRepositoryConfiguration {

    private final PasswordEncoder passwordEncoder;

    @DependsOn("serverAddress")
    @Bean
    public RegisteredClientRepository registeredClientRepository(
            TokenSettings settings,
            @Value("${server.address}")
            String address
    ) {
        return new InMemoryRegisteredClientRepository(
                RegisteredClient
                        .withId("test-client-id")
                        .clientName("Test Client")
                        .clientId("test-client")
                        .scope("read")
                        .scope("write")
                        .clientSecret(passwordEncoder.encode("test-client"))
                        .redirectUri("https://oauth.pstmn.io/v1/browser-callback")
                        .redirectUri("http://" + address + ":3000/auth/code")
                        .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                        .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                        .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                        .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                        .tokenSettings(settings)
                        .build()
        );
    }

    @Bean
    TokenSettings tokenSettings() {
        return TokenSettings.builder()
                .accessTokenFormat(OAuth2TokenFormat.REFERENCE)
                .accessTokenTimeToLive(Duration.of(30, ChronoUnit.MINUTES))
                .refreshTokenTimeToLive(Duration.of(120, ChronoUnit.MINUTES))
                .reuseRefreshTokens(false)
                .authorizationCodeTimeToLive(Duration.of(30, ChronoUnit.SECONDS))
                .build();
    }

}
