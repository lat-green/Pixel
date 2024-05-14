package com.example.pixel.server.chat.entity.room;

import com.example.pixel.server.chat.entity.ChatUser;
import com.example.pixel.server.chat.entity.message.ChatMessage;
import com.example.pixel.server.util.entity.BaseEntity;
import com.example.pixel.server.util.entity.EntityAsIdOnlySerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity(name = "chat_room")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class ChatRoom implements BaseEntity {

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false, unique = true)
    private String title;

    @JsonSerialize(using = EntityAsIdOnlySerializer.class)
    @OneToMany(mappedBy = "room", orphanRemoval = true, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    private Set<ChatMessage> messages = new HashSet<>();

    public abstract ChatRole getUserRole(ChatUser user);

    @AllArgsConstructor
    public enum ChatRole {

        NONE(false, false, false),
        READ(true, false, false),
        WRITE(true, true, false),
        ADMIN(true, true, true);

        public final boolean canRead, canWrite, isAdmin;
    }

}
