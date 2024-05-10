package com.example.pixel.server.authorization.configuration;

import com.example.pixel.server.authorization.controller.FilterChainExceptionHandler;
import com.example.pixel.server.authorization.entity.User;
import com.example.pixel.server.authorization.security.BearerOAuth2TokenAuthenticationFilter;
import com.example.pixel.server.authorization.security.BearerTokenAuthenticationProvider;
import com.example.pixel.server.authorization.security.UserServiceAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.server.resource.web.BearerTokenResolver;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerExceptionResolver;

import static org.springframework.security.config.Customizer.withDefaults;

@EnableWebSecurity
@RequiredArgsConstructor
@Configuration(proxyBeanMethods = false)
public class SecurityConfig {

    public static final String BEARER = "Bearer";
    public static final String HEADER_PREFIX = BEARER + " ";

    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(
            HttpSecurity http,
            AuthenticationManager manager,
            BearerTokenResolver bearerTokenResolver,
            @Qualifier("handlerExceptionResolver")
            HandlerExceptionResolver resolver
    ) throws Exception {
        return http
                .anonymous(c -> c.principal(
                        User
                                .builder()
                                .id(-1)
                                .username("anonymous")
                                .build()
                ))
//                .httpBasic(AbstractHttpConfigurer::disable)
//                .csrf(AbstractHttpConfigurer::disable)
//                .sessionManagement(c -> c.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize ->
                        authorize
                                .requestMatchers(HttpMethod.OPTIONS).permitAll()
                                .requestMatchers("/auth/**").permitAll()
                                .requestMatchers(HttpMethod.GET, "/api/**").permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/**").hasAuthority("SCOPE_write")
                                .requestMatchers(HttpMethod.PUT, "/api/**").hasAuthority("SCOPE_write")
                                .requestMatchers("/login/**").anonymous()
                                .anyRequest().authenticated()
                )
                .addFilterBefore(new FilterChainExceptionHandler(resolver), LogoutFilter.class)
                .addFilterBefore(new BearerOAuth2TokenAuthenticationFilter(bearerTokenResolver, manager), UsernamePasswordAuthenticationFilter.class)
                .formLogin(withDefaults())
                .build();
    }

    @Bean
    BearerTokenResolver bearerTokenResolver() {
        return request -> {
            String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);
            if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(HEADER_PREFIX)) {
                return bearerToken.substring(HEADER_PREFIX.length());
            }
            return null;
        };
    }

    @Bean
    AuthenticationManager customAuthenticationManager(
            HttpSecurity http,
            UserServiceAuthenticationProvider userServiceAuthenticationProvider,
            BearerTokenAuthenticationProvider bearearAuthenticationProvider
    ) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .authenticationProvider(userServiceAuthenticationProvider)
                .authenticationProvider(bearearAuthenticationProvider)
                .build();
    }

}