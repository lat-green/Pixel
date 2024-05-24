package com.example.pixel.server.chat.repository;

import com.example.pixel.server.chat.entity.Customer;
import com.example.pixel.server.chat.entity.attachment.GroupUserAttachment;
import com.example.pixel.server.chat.entity.chat.ChatGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GroupAttachmentRepository extends JpaRepository<GroupUserAttachment, Long> {

    Optional<GroupUserAttachment> findByGroupAndUser(ChatGroup group, Customer user);

}
