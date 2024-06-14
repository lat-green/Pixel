package com.example.pixel.server.chat.controller;

import com.example.pixel.server.chat.controller.securiry.HasScopeRead;
import com.example.pixel.server.chat.controller.securiry.HasScopeWrite;
import com.example.pixel.server.chat.dto.room.ChatGroupRoomCreateRequest;
import com.example.pixel.server.chat.entity.Customer;
import com.example.pixel.server.chat.entity.attachment.ChatChannelUserAttachment;
import com.example.pixel.server.chat.entity.chat.Chat;
import com.example.pixel.server.chat.entity.chat.ChatChannel;
import com.example.pixel.server.chat.entity.chat.ChatContact;
import com.example.pixel.server.chat.entity.chat.ChatGroup;
import com.example.pixel.server.chat.serializer.ChatUserAttachmentToRoomSerializer;
import com.example.pixel.server.chat.service.RoomService;
import com.example.pixel.server.util.controller.advice.exception.ForbiddenException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.web.bind.annotation.*;

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
    public Chat getOneRoom(@PathVariable long id) {
        return roomService.getOneRoom(id);
    }

    @GetMapping("/{id}/leave")
    public void leaveRoom(@PathVariable long id, Customer user) {
        roomService.leaveRoom(id, user);
    }

    @HasScopeRead
    @GetMapping("/{id}/users")
    public String getOneRoomUsers(@PathVariable long id, Customer user) throws JsonProcessingException {
        var room = roomService.getOneRoom(id);
        if (room instanceof ChatChannel channel) {
            var users = channel.getUsers();
            if (!users.stream().anyMatch(x -> x.getUser().equals(user)))
                throw new ForbiddenException("To receive a list of participants you must be a member");
            var json = objectMapper.writeValueAsString(users.stream().filter(x -> x.getRole() != ChatChannelUserAttachment.ChatChannelRole.PRIVATE_USER).toList());
            return json;
        }
        if (room instanceof ChatGroup channel) {
            var users = channel.getUsers();
            if (!users.stream().anyMatch(x -> x.getUser().equals(user)))
                throw new ForbiddenException("To receive a list of participants you must be a member");
            var json = objectMapper.writeValueAsString(users);
            return json;
        }
        if (room instanceof ChatContact channel) {
            var users = channel.getUsers();
            if (!users.stream().anyMatch(x -> x.getUser().equals(user)))
                throw new ForbiddenException("To receive a list of participants you must be a member");
            var json = objectMapper.writeValueAsString(users);
            return json;
        }
        throw new IllegalArgumentException();
    }

    @GetMapping("")
    public Collection<Chat> getAllRooms() {
        return roomService.getAllRooms();
    }

    @HasScopeWrite
    @PostMapping("/group")
    public Chat createGroupRoom(
            Customer user,
            @RequestBody ChatGroupRoomCreateRequest request
    ) {
        return roomService.createGroupRoom(user, request);
    }

}