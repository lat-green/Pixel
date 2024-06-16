package com.example.pixel.server.chat.service;

import com.example.pixel.server.chat.dto.room.ChatGroupRoomCreateRequest;
import com.example.pixel.server.chat.entity.Customer;
import com.example.pixel.server.chat.entity.attachment.ChatAttachment;
import com.example.pixel.server.chat.entity.attachment.GroupAttachment;
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
            if (chat.getUsers().isEmpty())
                repository.deleteById(room.getId());
            else
                repository.save(room);
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
        createAttachment(room, user, GroupAttachment.ChatGroupRole.ADMIN);
        return room;
    }

    private GroupAttachment createAttachment(ChatGroup group, Customer user, GroupAttachment.ChatGroupRole role) {
        return groupAttachmentRepository.findByGroupAndUser(group, user).orElseGet(() -> {
            var attachment = new GroupAttachment();
            attachment.setGroup(group);
            attachment.setUser(user);
            attachment.setRole(role);
            return groupAttachmentRepository.save(attachment);
        });
    }

    public ChatAttachment joinRoom(long roomId, Customer user) {
        var room = getOneRoom(roomId);
        return createAttachment(room, user);
    }

    private ChatAttachment createAttachment(Chat chat, Customer user) {
        if (chat instanceof ChatGroup group)
            return createAttachment(group, user, GroupAttachment.ChatGroupRole.USER);
        throw new UnsupportedOperationException("type = " + chat.getClass());
    }

}
