package com.example.pixel.server.chat.repository;

import com.example.pixel.server.chat.entity.attachment.ChatUserAttachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttachmentRepository extends JpaRepository<ChatUserAttachment, Long> {

}
