package com.example.pixel.server.chat.repository;

import com.example.pixel.server.chat.entity.Customer;
import com.example.pixel.server.chat.entity.attachment.ChatChannelUserAttachment;
import com.example.pixel.server.chat.entity.chat.ChatChannel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChannelAttachmentRepository extends JpaRepository<ChatChannelUserAttachment, Long> {

    Optional<ChatChannelUserAttachment> findByChannelAndUser(ChatChannel group, Customer user);

}
