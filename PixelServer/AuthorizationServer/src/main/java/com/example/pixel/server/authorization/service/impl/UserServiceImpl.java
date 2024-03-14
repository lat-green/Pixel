package com.example.pixel.server.authorization.service.impl;

import com.example.pixel.server.authorization.entity.User;
import com.example.pixel.server.authorization.service.UserService;
import com.example.pixel.server.authorization.exception.UserNotFoundException;
import com.example.pixel.server.authorization.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component("userService")
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.repository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));
    }

    @Override
    public User getOneUser(long id) {
        return this.repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

}
