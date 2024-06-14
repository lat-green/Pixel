package com.example.pixel.server.chat.service;

import com.example.pixel.server.chat.dto.room.ChatGroupRoomCreateRequest;
import com.example.pixel.server.chat.entity.Customer;
import com.example.pixel.server.chat.entity.attachment.GroupUserAttachment;
import com.example.pixel.server.chat.entity.chat.Chat;
import com.example.pixel.server.chat.entity.chat.ChatGroup;
import com.example.pixel.server.chat.exception.RoomNotFoundException;
import com.example.pixel.server.chat.repository.AttachmentRepository;
import com.example.pixel.server.chat.repository.GroupAttachmentRepository;
import com.example.pixel.server.chat.repository.RoomRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@AllArgsConstructor
@Component
public class RoomService {

    private RoomRepository repository;
    private AttachmentRepository attachmentRepository;
    private GroupAttachmentRepository groupAttachmentRepository;

    public Collection<Chat> getAllRooms() {
        return repository.findAll();
    }

    @Transactional
    public void leaveRoom(long id, Customer user) {
        var room = getOneRoom(id);
        var attachment = room.getAttachment(user);
        if (room instanceof ChatGroup chat) {
            chat.getUsers().remove(attachment);
            if (chat.getUsers().size() > 1)
                repository.save(room);
            else
                repository.deleteById(room.getId());
        }
    }

    public Chat getOneRoom(long id) {
        return repository.findById(id).orElseThrow(() -> new RoomNotFoundException(id));
    }

    public Chat createGroupRoom(
            Customer user,
            ChatGroupRoomCreateRequest request
    ) {
        var room = new ChatGroup();
        room.setTitle(request.getTitle());
        room = repository.save(room);
        createAttachment(room, user, GroupUserAttachment.ChatGroupRole.ADMIN);
        return room;
    }

    private GroupUserAttachment createAttachment(ChatGroup group, Customer user, GroupUserAttachment.ChatGroupRole role) {
        return groupAttachmentRepository.findByGroupAndUser(group, user).orElseGet(() -> {
            var attachment = new GroupUserAttachment();
            attachment.setGroup(group);
            attachment.setUser(user);
            attachment.setRole(role);
            return groupAttachmentRepository.save(attachment);
        });
    }

}
