package com.example.pixel.server.chat.repository;

import com.example.pixel.server.chat.entity.ChatUser;
import com.example.pixel.server.chat.entity.attachment.ChatChannelUserAttachment;
import com.example.pixel.server.chat.entity.room.ChatChannelRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChannelAttachmentRepository extends JpaRepository<ChatChannelUserAttachment, Long> {

    Optional<ChatChannelUserAttachment> findByChannelAndUser(ChatChannelRoom group, ChatUser user);

}
