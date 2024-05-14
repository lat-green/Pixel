package com.example.pixel.server.chat.service;

import com.example.pixel.server.chat.dto.room.ChatGroupRoomCreateRequest;
import com.example.pixel.server.chat.entity.ChatUser;
import com.example.pixel.server.chat.entity.attachment.ChatGroupUserAttachment;
import com.example.pixel.server.chat.entity.room.ChatGroupRoom;
import com.example.pixel.server.chat.entity.room.ChatRoom;
import com.example.pixel.server.chat.exception.RoomNotFoundException;
import com.example.pixel.server.chat.repository.GroupAttachmentRepository;
import com.example.pixel.server.chat.repository.RoomRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;

@AllArgsConstructor
@Component
public class RoomService {

    private RoomRepository repository;
    private GroupAttachmentRepository attachmentRepository;

    public Collection<ChatRoom> getAllRooms() {
        return repository.findAll();
    }

    public ChatRoom getOneRoom(long id) {
        return repository.findById(id).orElseThrow(() -> new RoomNotFoundException(id));
    }

    public ChatRoom createGroupRoom(
            ChatUser user,
            ChatGroupRoomCreateRequest request
    ) {
        var room = new ChatGroupRoom();
        room.setTitle(request.getTitle());
        room = repository.save(room);
        createAttachment(room, user, ChatGroupUserAttachment.ChatGroupRole.ADMIN);
        return room;
    }

    private ChatGroupUserAttachment createAttachment(ChatGroupRoom group, ChatUser user, ChatGroupUserAttachment.ChatGroupRole role) {
        return attachmentRepository.findByGroupAndUser(group, user).orElseGet(() -> {
            var attachment = new ChatGroupUserAttachment();
            attachment.setGroup(group);
            attachment.setUser(user);
            attachment.setRole(role);
            return attachmentRepository.save(attachment);
        });
    }

}
