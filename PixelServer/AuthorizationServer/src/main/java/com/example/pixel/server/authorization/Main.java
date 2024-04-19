package com.example.pixel.server.authorization;

import com.example.pixel.server.util.EnableUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.PropertySource;

@EnableUtil
@PropertySource("classpath:application-secret.properties")
//@PropertySource("classpath:org/springframework/security/oauth2/server/authorization/oauth2-authorization-schema.sql")
@SpringBootApplication
@ConfigurationPropertiesScan
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

}
