package com.example.pixel.server.chat.controller;

import com.example.pixel.server.chat.dto.message.ChatTextMessageCreateRequest;
import com.example.pixel.server.chat.entity.ChatUser;
import com.example.pixel.server.chat.entity.message.ChatMessage;
import com.example.pixel.server.chat.service.MessageService;
import com.example.pixel.server.chat.service.RoomService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class RoomMessageController {

    private final MessageService messageService;
    private final RoomService roomService;

    @PostMapping("/rooms/{roomId}/messages")
    public ChatMessage createTextMessage(@PathVariable long roomId, ChatUser user, @RequestBody ChatTextMessageCreateRequest request) {
        return messageService.createTextMessage(roomId, user, request);
    }

}