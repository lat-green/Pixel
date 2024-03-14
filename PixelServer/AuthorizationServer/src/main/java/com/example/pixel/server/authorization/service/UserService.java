package com.example.pixel.server.authorization.service;

import com.example.pixel.server.authorization.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserService extends UserDetailsService {

    @Override
    User loadUserByUsername(String username) throws UsernameNotFoundException;

    User getOneUser(long id);

}
