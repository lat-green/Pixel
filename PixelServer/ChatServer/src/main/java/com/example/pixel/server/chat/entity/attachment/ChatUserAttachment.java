package com.example.pixel.server.chat.entity.attachment;

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

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "chat_user_attachment")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class ChatUserAttachment implements BaseEntity {

    @Id
    @GeneratedValue
    private long id;

    @JsonSerialize(using = EntityAsIdOnlySerializer.class)
    @ManyToOne(optional = false, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Customer user;

    public abstract Chat getChatRoom();

}
