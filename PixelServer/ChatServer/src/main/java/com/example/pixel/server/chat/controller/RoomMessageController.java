package com.example.pixel.server.chat.controller;

import com.example.pixel.server.chat.controller.securiry.HasScopeRead;
import com.example.pixel.server.chat.controller.securiry.HasScopeWrite;
import com.example.pixel.server.chat.dto.message.TextMessageCreateRequest;
import com.example.pixel.server.chat.entity.Customer;
import com.example.pixel.server.chat.entity.message.Message;
import com.example.pixel.server.chat.service.MessageService;
import com.example.pixel.server.chat.service.RoomService;
import com.example.pixel.server.util.controller.advice.exception.ForbiddenException;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class RoomMessageController {

    private final MessageService messageService;
    private final RoomService roomService;

    @HasScopeWrite
    @PostMapping("/rooms/{roomId}/messages/text")
    public Message createTextMessage(@PathVariable long roomId, Customer user, @RequestBody TextMessageCreateRequest request) {
        return messageService.createTextMessage(roomId, user, request);
    }

    @HasScopeRead
    @GetMapping("/rooms/{roomId}/messages")
    public List<Message> getAllMessages(@PathVariable long roomId, Customer user) {
        var room = roomService.getOneRoom(roomId);
        if (!room.getUserRole(user).canRead)
            throw new ForbiddenException("to get messages of chat you will be customer of it's chat");
        return messageService.getAllMessagesOfRoom(roomId);
    }

}