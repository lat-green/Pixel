package com.example.pixel.server.chat.model;

import com.example.pixel.server.util.entity.EntityAsIdOnlySerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class ChatRoom {

    @Id
    @GeneratedValue
    private Long id;

    @JsonSerialize(using = EntityAsIdOnlySerializer.class)
    @OneToMany(mappedBy = "room", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<ChatMessage> messages = List.of();

    @JsonSerialize(using = EntityAsIdOnlySerializer.class)
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private ChatUser sender;

    @JsonSerialize(using = EntityAsIdOnlySerializer.class)
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private ChatUser recipient;

}
