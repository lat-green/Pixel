package com.example.pixel.server.chat.service;

import com.example.pixel.server.chat.entity.room.ChatRoom;
import com.example.pixel.server.chat.exception.RoomNotFoundException;
import com.example.pixel.server.chat.repository.RoomRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;

@AllArgsConstructor
@Component
public class RoomService {

    private RoomRepository repository;

    public Collection<ChatRoom> getAllRooms() {
        return repository.findAll();
    }

    public ChatRoom getOneRoom(long id) {
        return repository.findById(id).orElseThrow(() -> new RoomNotFoundException(id));
    }

}
