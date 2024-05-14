package com.example.pixel.server.chat.repository;

import com.example.pixel.server.chat.entity.ChatUser;
import com.example.pixel.server.chat.entity.attachment.ChatGroupUserAttachment;
import com.example.pixel.server.chat.entity.room.ChatGroupRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GroupAttachmentRepository extends JpaRepository<ChatGroupUserAttachment, Long> {

    Optional<ChatGroupUserAttachment> findByGroupAndUser(ChatGroupRoom group, ChatUser user);

}
