package com.example.pixel.server.chat.repository;

import com.example.pixel.server.chat.entity.ChatUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<ChatUser, Long> {

    Optional<ChatUser> findByUsername(String username);

}
