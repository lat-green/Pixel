package com.example.pixel.server.authorization.service;

import com.example.pixel.server.authorization.entity.AuthUser;
import com.example.pixel.server.authorization.repository.UserRepository;
import com.example.pixel.server.util.controller.advice.exception.UserNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UserService implements UserDetailsService {

    private UserRepository repository;

    @Override
    public AuthUser loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.repository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
    }

}