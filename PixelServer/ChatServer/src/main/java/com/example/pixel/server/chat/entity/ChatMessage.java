package com.example.pixel.server.chat.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "chat_message")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class ChatMessage {

    @Id
    @GeneratedValue
    private long id;

}
