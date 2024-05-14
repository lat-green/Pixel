package com.example.pixel.server.chat.controller;

import com.example.pixel.server.chat.entity.ChatUser;
import com.example.pixel.server.chat.entity.room.ChatChannelRoom;
import com.example.pixel.server.chat.entity.room.ChatGroupRoom;
import com.example.pixel.server.chat.entity.room.ChatRoom;
import com.example.pixel.server.chat.serializer.ChatUserAttachmentToRoomSerializer;
import com.example.pixel.server.chat.service.RoomService;
import com.example.pixel.server.util.controller.advice.exception.ForbiddenException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/rooms")
public class RoomController {

    private final RoomService roomService;

    private final ObjectMapper objectMapper;

    public RoomController(RoomService roomService, ChatUserAttachmentToRoomSerializer serializer) {
        this.roomService = roomService;
        this.objectMapper = new ObjectMapper().registerModule(new SimpleModule().addSerializer(serializer));
    }

    @GetMapping("/{id}")
    public ChatRoom getOneRoom(@PathVariable long id) {
        return roomService.getOneRoom(id);
    }

    @GetMapping("/{id}/users")
    public String getOneRoomUsers(@PathVariable long id, ChatUser user) throws JsonProcessingException {
        var room = roomService.getOneRoom(id);
        if (room instanceof ChatChannelRoom channel) {
            var users = channel.getUsers();
            if (!users.stream().anyMatch(x -> x.getUser().equals(user)))
                throw new ForbiddenException("To receive a list of participants you must be a member");
            var json = objectMapper.writeValueAsString(users);
            return json;
        }
        if (room instanceof ChatGroupRoom channel) {
            var users = channel.getUsers();
            if (!users.stream().anyMatch(x -> x.getUser().equals(user)))
                throw new ForbiddenException("To receive a list of participants you must be a member");
            var json = objectMapper.writeValueAsString(users);
            return json;
        }
        throw new IllegalArgumentException();
    }

    @GetMapping("")
    public Collection<ChatRoom> getAllRooms() {
        return roomService.getAllRooms();
    }

}