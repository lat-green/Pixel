package com.example.pixel.server.chat.configuration;

import com.example.pixel.server.chat.entity.ChatUser;
import com.example.pixel.server.chat.entity.attachment.ChatChannelUserAttachment;
import com.example.pixel.server.chat.entity.attachment.ChatGroupUserAttachment;
import com.example.pixel.server.chat.entity.message.ChatTextMessage;
import com.example.pixel.server.chat.entity.room.ChatChannelRoom;
import com.example.pixel.server.chat.entity.room.ChatGroupRoom;
import com.example.pixel.server.chat.entity.room.ChatRoom;
import com.example.pixel.server.chat.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.transaction.annotation.Transactional;

import java.net.MalformedURLException;

@AllArgsConstructor
@Configuration
public class RepositoryConfiguration {

    private final UserRepository userRepository;
    private final MessageRepository messageRepository;
    private final TextMessageRepository textMessageRepository;
    private final RoomRepository roomRepository;
    private final GroupAttachmentRepository groupAttachmentRepository;
    private final ChannelAttachmentRepository channelAttachmentRepository;

    @Transactional
    @EventListener(ApplicationReadyEvent.class)
    public void init() throws MalformedURLException {
        var arseny = userRepository.findByUsername("Arseny").orElseThrow();
        var maksim = userRepository.findByUsername("Maksim").orElseThrow();
        var g1 = createGroup("Group 1");
        var g2 = createGroup("Group 2");
        var c1 = createChannel("Channel 1");
        createAttachment(g1, arseny, ChatGroupUserAttachment.ChatGroupRole.ADMIN);
        createAttachment(g2, arseny, ChatGroupUserAttachment.ChatGroupRole.ADMIN);
        createAttachment(g2, arseny, ChatGroupUserAttachment.ChatGroupRole.USER);
        createAttachment(c1, arseny, ChatChannelUserAttachment.ChatChannelRole.ADMIN);
        createTextMessage(arseny, g1, "Message 1");
        createTextMessage(arseny, g1, "Message 2");
        createTextMessage(arseny, c1, "Message 3");
        createTextMessage(maksim, g2, "Message 4");
    }

    private ChatGroupRoom createGroup(String title) {
        return (ChatGroupRoom) roomRepository.findByTitle(title).orElseGet(() -> {
            var room = new ChatGroupRoom();
            room.setTitle(title);
            return roomRepository.save(room);
        });
    }

    private ChatChannelRoom createChannel(String title) {
        return (ChatChannelRoom) roomRepository.findByTitle(title).orElseGet(() -> {
            var room = new ChatChannelRoom();
            room.setTitle(title);
            return roomRepository.save(room);
        });
    }

    private ChatGroupUserAttachment createAttachment(ChatGroupRoom group, ChatUser user, ChatGroupUserAttachment.ChatGroupRole role) {
        return groupAttachmentRepository.findByGroupAndUser(group, user).orElseGet(() -> {
            var attachment = new ChatGroupUserAttachment();
            attachment.setGroup(group);
            attachment.setUser(user);
            attachment.setRole(role);
            return groupAttachmentRepository.save(attachment);
        });
    }

    private ChatChannelUserAttachment createAttachment(ChatChannelRoom channel, ChatUser user, ChatChannelUserAttachment.ChatChannelRole role) {
        return channelAttachmentRepository.findByChannelAndUser(channel, user).orElseGet(() -> {
            var attachment = new ChatChannelUserAttachment();
            attachment.setChannel(channel);
            attachment.setUser(user);
            attachment.setRole(role);
            return channelAttachmentRepository.save(attachment);
        });
    }

    private ChatTextMessage createTextMessage(ChatUser user, ChatRoom room, String content) {
        return textMessageRepository.findByRoomAndUserAndContent(room, user, content).orElseGet(() -> {
            var message = new ChatTextMessage();
            message.setContent(content);
            message.setUser(user);
            message.setRoom(room);
            return textMessageRepository.save(message);
        });
    }

}
