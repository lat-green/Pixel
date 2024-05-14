package com.example.pixel.server.chat.repository;

import com.example.pixel.server.chat.entity.room.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<ChatRoom, Long> {

    Optional<ChatRoom> findByTitle(String title);

}
