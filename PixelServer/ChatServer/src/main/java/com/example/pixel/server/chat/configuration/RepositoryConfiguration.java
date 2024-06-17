package com.example.pixel.server.chat.configuration;

import com.example.pixel.server.chat.entity.Customer;
import com.example.pixel.server.chat.entity.attachment.ChannelAttachment;
import com.example.pixel.server.chat.entity.attachment.ContactAttachment;
import com.example.pixel.server.chat.entity.attachment.GroupAttachment;
import com.example.pixel.server.chat.entity.chat.Chat;
import com.example.pixel.server.chat.entity.chat.ChatChannel;
import com.example.pixel.server.chat.entity.chat.ChatContact;
import com.example.pixel.server.chat.entity.chat.ChatGroup;
import com.example.pixel.server.chat.entity.message.TextUserMessage;
import com.example.pixel.server.chat.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

import static java.lang.Thread.sleep;

@AllArgsConstructor
@Configuration
public class RepositoryConfiguration {

    private final UserRepository userRepository;
    private final UserMessageRepository messageRepository;
    private final TextMessageRepository textMessageRepository;
    private final RoomRepository roomRepository;
    private final GroupAttachmentRepository groupAttachmentRepository;
    private final ChannelAttachmentRepository channelAttachmentRepository;
    private final ContactAttachmentRepository contactAttachmentRepository;
    private final AttachmentRepository attachmentRepository;

    @Transactional
    @EventListener(ApplicationReadyEvent.class)
    public void init() {
        messageRepository.deleteAll();
        updateUsersCreateDate();
        var u1 = userRepository.findByUsername("Света").orElseThrow();
        var u2 = userRepository.findByUsername("Ангелина").orElseThrow();
        var u3 = userRepository.findByUsername("Никита").orElseThrow();
        var u4 = userRepository.findByUsername("Татьяна Василевна").orElseThrow();
        var g1 = createContact(u1, u2);
        createTextMessage(u2, g1, "Привет, Света! Ты не знаешь, что нам задали по литературе на завтра?");
        createTextMessage(u1, g1, "Знаю: нужно написать сочинение.");
        createTextMessage(u2, g1, "На какую тему?");
        createTextMessage(u1, g1, "На свободную. Ты сама можешь выбрать то, о чем ты хочешь рассказать.");
        createTextMessage(u2, g1, "Тогда, пожалуй, напишу о том, как прошли осенние каникулы.");
        createTextMessage(u1, g1, "Это хорошая идея, а я буду писать о том, кем я хочу стать.");
        createTextMessage(u2, g1, "Спасибо тебе большое за информацию. Пойду готовиться.");
        var g2 = createGroup("11 'Б'");
        createAttachment(g2, u1, GroupAttachment.ChatGroupRole.ADMIN);
        createAttachment(g2, u2, GroupAttachment.ChatGroupRole.USER);
        createAttachment(g2, u3, GroupAttachment.ChatGroupRole.USER);
        createTextMessage(u1, g2, "Привет, Никита");
        createTextMessage(u3, g2, "Привет, Света");
        createTextMessage(u1, g2, "А какой у нас сейчас урок?");
        createTextMessage(u2, g2, "Русский язык");
        createTextMessage(u1, g2, "Спасибо Ангелина");
        createTextMessage(u3, g2, "А ты написала диолог на тему в школе на перемене?");
        createTextMessage(u2, g2, "Нет, а ты");
        createTextMessage(u3, g2, "Я да");
        var g3 = createChannel("Школа 109");
        createAttachment(g3, u4, ChannelAttachment.ChatChannelRole.ADMIN);
        createAttachment(g3, u1, ChannelAttachment.ChatChannelRole.PUBLIC_USER);
        createAttachment(g3, u2, ChannelAttachment.ChatChannelRole.PUBLIC_USER);
        createAttachment(g3, u3, ChannelAttachment.ChatChannelRole.PUBLIC_USER);
        createTextMessage(u4, g3, """
                В период летних каникул для учащихся 1-8 классов ГУО "Средняя школа №109 имени П.И. Куприянова
                 г. Жодино" в период с 03.06.2024 по 21.06.2024 организована работа оздоровительного лагеря "Здравиус".
                """);
        createTextMessage(u4, g3, """
                Участились случаи шалости учащихся вблизи железной дороги.
                Бесконтрольность детей приводит их на железнодорожное полотно.
                Школьники подкладывают на пути посторонние предметы!
                В результате машинисты были вынуждены применить экстренное торможение!
                Детская шалость - результат недосмотра взрослых!
                Следите за своими детьми!
                Помните, вы несёте ответственность за их поведение!
                """);
    }

    private void updateUsersCreateDate() {
        userRepository.findAll().forEach(x -> {
            if (x.getCreatedDate() == null) {
                x.setCreatedDate(new Date());
                userRepository.save(x);
            }
        });
    }

    private ChatContact createContact(Customer sender, Customer recipient) {
        var list = new ArrayList<String>();
        list.add(sender.getUsername());
        list.add(recipient.getUsername());
        Collections.sort(list);
        var unionName = String.join(" ", list);
        var contact = (ChatContact) roomRepository.findByTitle(unionName).orElseGet(() -> {
            var room = new ChatContact();
            room.setTitle(unionName);
            return roomRepository.save(room);
        });
        createAttachment(contact, sender);
        createAttachment(contact, recipient);
        return contact;
    }

    private TextUserMessage createTextMessage(Customer user, Chat room, String content) {
        return textMessageRepository.findByChatAndUserAndContent(room, user, content).orElseGet(() -> {
            try {
                sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            var message = new TextUserMessage();
            message.setContent(content);
            message.setUser(user);
            message.setChat(room);
            message.getSendTime().setMonth(Calendar.MAY);
            message.getSendTime().setHours(13);
            return textMessageRepository.save(message);
        });
    }

    private ChatGroup createGroup(String title) {
        return (ChatGroup) roomRepository.findByTitle(title).orElseGet(() -> {
            var room = new ChatGroup();
            room.setTitle(title);
            return roomRepository.save(room);
        });
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

    private ChatChannel createChannel(String title) {
        return (ChatChannel) roomRepository.findByTitle(title).orElseGet(() -> {
            var room = new ChatChannel();
            room.setTitle(title);
            return roomRepository.save(room);
        });
    }

    private ChannelAttachment createAttachment(ChatChannel channel, Customer user, ChannelAttachment.ChatChannelRole role) {
        return channelAttachmentRepository.findByChannelAndUser(channel, user).orElseGet(() -> {
            var attachment = new ChannelAttachment();
            attachment.setChannel(channel);
            attachment.setUser(user);
            attachment.setRole(role);
            return channelAttachmentRepository.save(attachment);
        });
    }

    private ContactAttachment createAttachment(ChatContact contact, Customer user) {
        return contactAttachmentRepository.findByContactAndUser(contact, user).orElseGet(() -> {
            var attachment = new ContactAttachment();
            attachment.setContact(contact);
            attachment.setUser(user);
            return contactAttachmentRepository.save(attachment);
        });
    }

}
