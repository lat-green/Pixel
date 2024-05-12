package com.example.pixel.server.chat.service;

import com.example.pixel.server.chat.entity.ChatUser;
import com.example.pixel.server.chat.repository.UserRepository;
import com.example.pixel.server.util.controller.advice.exception.UserNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;

@AllArgsConstructor
@Component
public class UserService {

    private UserRepository repository;

    public Collection<ChatUser> getAllUsers() {
        return repository.findAll();
    }

    public void deleteUser(long id) {
        repository.deleteById(id);
    }
//    @Transactional
//    public User replaceUser(long id, UserReplaceRequest request) {
//        var user = getOneUser(id);
//        var username = request.getUsername();
//        if (username != null)
//            user.setUsername(username);
//        var password = request.getPassword();
//        if (password != null)
//            user.setUsername(password);
//        return repository.save(user);
//    }

    public ChatUser getOneUser(long id) {
        return repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

}