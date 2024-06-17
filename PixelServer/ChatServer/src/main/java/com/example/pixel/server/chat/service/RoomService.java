package com.example.pixel.server.chat.service;

import com.example.pixel.server.chat.dto.room.ChatGroupRoomCreateRequest;
import com.example.pixel.server.chat.entity.Customer;
import com.example.pixel.server.chat.entity.attachment.ChatAttachment;
import com.example.pixel.server.chat.entity.attachment.GroupAttachment;
import com.example.pixel.server.chat.entity.chat.Chat;
import com.example.pixel.server.chat.entity.chat.ChatGroup;
import com.example.pixel.server.chat.entity.message.SystemMessage;
import com.example.pixel.server.chat.exception.RoomNotFoundException;
import com.example.pixel.server.chat.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Date;

@AllArgsConstructor
@Component
public class RoomService {

    private final SystemMessageRepository systemMessageRepository;
    private RoomRepository repository;
    private AttachmentRepository attachmentRepository;
    private GroupAttachmentRepository groupAttachmentRepository;
    private MessageRepository messageRepository;

    public Collection<Chat> getAllRooms() {
        return repository.findAll();
    }

    @Transactional
    public void leaveRoom(long id, Customer user) {
        var room = getOneRoom(id);
        var attachment = room.getAttachment(user);
        if (room instanceof ChatGroup chat) {
            if (chat.getUsers().remove(attachment))
                if (chat.getUsers().isEmpty())
                    repository.deleteById(room.getId());
                else {
                    createSystemMessage(room, user.getUsername() + " leave");
                    repository.save(room);
                }
        }
    }

    public Chat getOneRoom(long id) {
        return repository.findById(id).orElseThrow(() -> new RoomNotFoundException(id));
    }

    private void createSystemMessage(Chat chat, String content) {
        var messages = systemMessageRepository.findAllByChatAndContent(chat, content);
        var now = new Date();
        if (messages.stream().noneMatch(x -> x.getSendTime().getTime() - now.getTime() < 10_000)) {
            var message = new SystemMessage();
            message.setChat(chat);
            message.setContent(content);
            messageRepository.save(message);
        }
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

    public ChatAttachment updateLastRead(long roomId, Customer user) {
        var chat = getOneRoom(roomId);
        var attachment = chat.getAttachment(user);
        attachment.updateLastRead();
        attachmentRepository.save(attachment);
        return attachment;
    }

    public ChatAttachment joinRoom(long roomId, Customer user) {
        var room = getOneRoom(roomId);
        if (room.getUserRole(user) != Chat.ChatRole.NONE) {
            var attachment = createAttachment(room, user);
            createSystemMessage(room, user.getUsername() + " join");
            return attachment;
        }
        return createAttachment(room, user);
    }

    private ChatAttachment createAttachment(Chat chat, Customer user) {
        if (chat instanceof ChatGroup group)
            return createAttachment(group, user, GroupAttachment.ChatGroupRole.USER);
        throw new UnsupportedOperationException("type = " + chat.getClass());
    }

}
