package com.example.pixel.server.chat.repository;

import com.example.pixel.server.chat.entity.Customer;
import com.example.pixel.server.chat.entity.attachment.ChannelAttachment;
import com.example.pixel.server.chat.entity.chat.ChatChannel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChannelAttachmentRepository extends JpaRepository<ChannelAttachment, Long> {

    Optional<ChannelAttachment> findByChannelAndUser(ChatChannel group, Customer user);

}
