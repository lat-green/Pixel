package com.example.pixel.server.chat.entity.attachment;

import com.example.pixel.server.chat.entity.Customer;
import com.example.pixel.server.chat.entity.chat.Chat;
import com.example.pixel.server.util.entity.BaseEntity;
import com.example.pixel.server.util.entity.EntityAsIdOnlySerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Entity(name = "attachment")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class ChatAttachment implements BaseEntity {

    @Id
    @GeneratedValue
    private long id;

    @JsonSerialize(using = EntityAsIdOnlySerializer.class)
    @ManyToOne(optional = false, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Customer user;

    @Column(nullable = false)
    private Date lastRead = new Date();

    @JsonSerialize(using = EntityAsIdOnlySerializer.class)
    public abstract Chat getChat();

    @JsonIgnore
    public Date updateLastRead() {
        var date = new Date();
        setLastRead(date);
        return date;
    }

}
