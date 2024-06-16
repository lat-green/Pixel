package com.example.pixel.server.chat.repository;

import com.example.pixel.server.chat.entity.chat.ChatContact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepository extends JpaRepository<ChatContact, Long> {

}
