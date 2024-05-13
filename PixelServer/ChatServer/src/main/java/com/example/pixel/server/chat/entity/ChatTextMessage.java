package com.example.pixel.server.chat.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Entity(name = "chat_text_message")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatTextMessage extends ChatMessage {

    @Column(nullable = false)
    private String content;

}
