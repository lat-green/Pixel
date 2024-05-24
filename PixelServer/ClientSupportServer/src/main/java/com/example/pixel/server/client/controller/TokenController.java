package com.example.pixel.server.client.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@AllArgsConstructor
@RestController
public class TokenController {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @PostMapping("/token")
    ResponseEntity<String> tokens(@RequestParam MultiValueMap<String, String> body) throws IOException {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            headers.setBasicAuth("test-client", "test-client");
            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);
            var response = restTemplate.postForEntity("http://localhost:7777/oauth2/token", request, String.class);
            return response;
        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsString());
        }
    }

}
