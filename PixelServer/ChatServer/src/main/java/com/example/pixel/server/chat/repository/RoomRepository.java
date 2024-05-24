package com.example.pixel.server.chat.repository;

import com.example.pixel.server.chat.entity.chat.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Chat, Long> {

    Optional<Chat> findByTitle(String title);

}
