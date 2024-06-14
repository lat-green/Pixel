package com.example.pixel.server.chat.entity.chat;

import com.example.pixel.server.chat.entity.Customer;
import com.example.pixel.server.chat.entity.attachment.ChatUserAttachment;
import com.example.pixel.server.chat.entity.message.Message;
import com.example.pixel.server.util.entity.BaseEntity;
import com.example.pixel.server.util.entity.EntityAsIdOnlySerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "chat_room")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Chat implements BaseEntity {

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false, unique = true)
    private String title;

    @JsonSerialize(using = EntityAsIdOnlySerializer.class)
    @OneToMany(mappedBy = "chat", orphanRemoval = true, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    private Set<Message> messages = new HashSet<>();

    @Column(name = "created_date", nullable = false, updatable = false)
    private Date createdDate = new Date();

    public abstract ChatRole getUserRole(Customer user);

    public abstract ChatUserAttachment getAttachment(Customer user);

    @AllArgsConstructor
    public enum ChatRole {

        NONE(false, false, false),
        READ(true, false, false),
        WRITE(true, true, false),
        ADMIN(true, true, true);

        public final boolean canRead, canWrite, isAdmin;
    }

}
