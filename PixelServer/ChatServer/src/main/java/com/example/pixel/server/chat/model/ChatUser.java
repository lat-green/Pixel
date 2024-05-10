package com.example.pixel.server.chat.model;

import com.example.pixel.server.util.entity.EntityAsIdOnlySerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity(name = "user")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatUser {

    @Id
    @GeneratedValue
    long id;

    @Column(nullable = false, unique = true)
    private String username;

    @JsonSerialize(using = EntityAsIdOnlySerializer.class)
    @OneToMany(mappedBy = "sender", orphanRemoval = true)
    private Set<ChatRoom> senderIn;

    @JsonSerialize(using = EntityAsIdOnlySerializer.class)
    @OneToMany(mappedBy = "recipient", orphanRemoval = true)
    private Set<ChatRoom> resipientIn;

}
