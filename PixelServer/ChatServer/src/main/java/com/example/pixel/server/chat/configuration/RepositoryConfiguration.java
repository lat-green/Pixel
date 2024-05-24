package com.example.pixel.server.chat.configuration;

import com.example.pixel.server.chat.entity.Customer;
import com.example.pixel.server.chat.entity.attachment.ChatChannelUserAttachment;
import com.example.pixel.server.chat.entity.attachment.GroupUserAttachment;
import com.example.pixel.server.chat.entity.chat.Chat;
import com.example.pixel.server.chat.entity.chat.ChatChannel;
import com.example.pixel.server.chat.entity.chat.ChatGroup;
import com.example.pixel.server.chat.entity.message.TextMessage;
import com.example.pixel.server.chat.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.transaction.annotation.Transactional;

import java.net.MalformedURLException;
import java.util.Date;

import static java.lang.Thread.sleep;

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
    public void init() throws MalformedURLException, InterruptedException {
        updateUsersCreateDate();
        var u1 = userRepository.findByUsername("Света").orElseThrow();
        var u2 = userRepository.findByUsername("Ангелина").orElseThrow();
        var g1 = createGroup(u2.getUsername());
        createAttachment(g1, u1, GroupUserAttachment.ChatGroupRole.ADMIN);
        createAttachment(g1, u2, GroupUserAttachment.ChatGroupRole.USER);
        createTextMessage(u1, g1, "Привет, Ангелина!");
        createTextMessage(u2, g1, "Привет, Света! Ты не знаешь, что нам задали по литературе на завтра?");
        createTextMessage(u1, g1, "Знаю: нужно написать сочинение.");
        createTextMessage(u2, g1, "На какую тему?");
        createTextMessage(u1, g1, "На свободную. Ты сама можешь выбрать то, о чем ты хочешь рассказать.");
        createTextMessage(u2, g1, "Тогда, пожалуй, напишу о том, как прошли осенние каникулы.");
        createTextMessage(u1, g1, "Это хорошая идея, а я буду писать о том, кем я хочу стать.");
        createTextMessage(u2, g1, "Спасибо тебе большое за информацию. Пойду готовиться.");
    }

    private void updateUsersCreateDate() {
        userRepository.findAll().forEach(x -> {
            if (x.getCreatedDate() == null) {
                x.setCreatedDate(new Date());
                userRepository.save(x);
            }
        });
    }

    private ChatGroup createGroup(String title) {
        return (ChatGroup) roomRepository.findByTitle(title).orElseGet(() -> {
            var room = new ChatGroup();
            room.setTitle(title);
            return roomRepository.save(room);
        });
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

    private TextMessage createTextMessage(Customer user, Chat room, String content) {
        return textMessageRepository.findByChatAndUserAndContent(room, user, content).orElseGet(() -> {
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            var message = new TextMessage();
            message.setContent(content);
            message.setUser(user);
            message.setChat(room);
            return textMessageRepository.save(message);
        });
    }

    private ChatChannel createChannel(String title) {
        return (ChatChannel) roomRepository.findByTitle(title).orElseGet(() -> {
            var room = new ChatChannel();
            room.setTitle(title);
            return roomRepository.save(room);
        });
    }

    private ChatChannelUserAttachment createAttachment(ChatChannel channel, Customer user, ChatChannelUserAttachment.ChatChannelRole role) {
        return channelAttachmentRepository.findByChannelAndUser(channel, user).orElseGet(() -> {
            var attachment = new ChatChannelUserAttachment();
            attachment.setChannel(channel);
            attachment.setUser(user);
            attachment.setRole(role);
            return channelAttachmentRepository.save(attachment);
        });
    }

}
