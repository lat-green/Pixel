package com.example.pixel.server.authorization.config;

import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Map;

@AllArgsConstructor
@Configuration
public class IpUtil {

    private final ConfigurableEnvironment env;

    @Bean
    public MapPropertySource serverAddress() throws IOException {
        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress("google.com", 80));
            val address = socket.getLocalAddress().getHostAddress();
            val propertySource = new MapPropertySource("server-address", Map.of("server.address", address));
            env.getPropertySources().addFirst(propertySource);
            return propertySource;
        }
    }

}
