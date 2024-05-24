package com.example.pixel.server.chat.entity.message;

import com.example.pixel.server.chat.entity.Customer;
import com.example.pixel.server.chat.entity.chat.Chat;
import com.example.pixel.server.util.entity.BaseEntity;
import com.example.pixel.server.util.entity.EntityAsIdOnlySerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "chat_message")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Message implements BaseEntity {

    @Id
    @GeneratedValue
    protected long id;

    @Column(nullable = false)
    protected Date sendTime = new Date();

    @JsonSerialize(using = EntityAsIdOnlySerializer.class)
    @ManyToOne(optional = false, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @OnDelete(action = OnDeleteAction.CASCADE)
    protected Customer user;

    @JsonSerialize(using = EntityAsIdOnlySerializer.class)
    @ManyToOne(optional = false, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @OnDelete(action = OnDeleteAction.CASCADE)
    protected Chat chat;

}
