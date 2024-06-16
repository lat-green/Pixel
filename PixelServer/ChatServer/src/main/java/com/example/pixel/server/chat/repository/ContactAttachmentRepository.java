package com.example.pixel.server.chat.repository;

import com.example.pixel.server.chat.entity.Customer;
import com.example.pixel.server.chat.entity.attachment.ContactAttachment;
import com.example.pixel.server.chat.entity.chat.ChatContact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContactAttachmentRepository extends JpaRepository<ContactAttachment, Long> {

    Optional<ContactAttachment> findByContactAndUser(ChatContact contact, Customer user);

}
