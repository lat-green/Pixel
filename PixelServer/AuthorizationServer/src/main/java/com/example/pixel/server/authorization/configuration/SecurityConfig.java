package com.example.pixel.server.authorization.configuration;

import com.example.pixel.server.authorization.entity.AuthUser;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

import static org.springframework.security.config.Customizer.withDefaults;

@EnableWebSecurity
@RequiredArgsConstructor
@Configuration
public class SecurityConfig {

    @Bean
    public CorsFilter corsFilter() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(false);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("OPTIONS");
        config.addAllowedMethod("HEAD");
        config.addAllowedMethod("GET");
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("DELETE");
        config.addAllowedMethod("PATCH");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

    @Bean
    public OAuth2TokenCustomizer<JwtEncodingContext> accessTokenCustomizer() {
        return context -> {
            if (OAuth2TokenType.ACCESS_TOKEN.equals(context.getTokenType())) {
                var principal = context.getPrincipal().getPrincipal();
                if (principal instanceof AuthUser user) {
                    var userId = user.getId();
                    context.getClaims().claims(claims -> {
                        claims.put("user_id", userId);
                    });
                }
            }
        };
    }

    @Bean
    @Order(1)
    SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http) throws Exception {
        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);
        http.getConfigurer(OAuth2AuthorizationServerConfigurer.class)
                .oidc(withDefaults());    // Enable OpenID Connect 1.0
        return http.formLogin(withDefaults()).build();
    }

    @Bean
    @Order(2)
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests.anyRequest().authenticated()
                )
                .formLogin(withDefaults());
        return http.build();
    }

    @Bean
    ClientSettings clientSettings() {
        return ClientSettings.builder()
                .build();
    }

    @Bean
    TokenSettings tokenSettings() {
        return TokenSettings.builder()
                .accessTokenTimeToLive(Duration.of(30, ChronoUnit.DAYS))
                .refreshTokenTimeToLive(Duration.of(3, ChronoUnit.DAYS))
                .authorizationCodeTimeToLive(Duration.of(30, ChronoUnit.MINUTES))
                .build();
    }

}