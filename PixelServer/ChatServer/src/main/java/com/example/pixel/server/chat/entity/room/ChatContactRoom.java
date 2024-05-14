package com.example.pixel.server.chat.entity.room;

import com.example.pixel.server.chat.entity.ChatUser;
import com.example.pixel.server.chat.entity.attachment.ChatContactUserAttachment;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.Set;

import static com.example.pixel.server.chat.entity.room.ChatRoom.ChatRole.ADMIN;
import static com.example.pixel.server.chat.entity.room.ChatRoom.ChatRole.NONE;

@Entity(name = "chat_contact_room")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatContactRoom extends ChatRoom {

    @JsonIgnore
    @OneToMany(mappedBy = "contact", orphanRemoval = true, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    private Set<ChatContactUserAttachment> users;

    @Override
    public ChatRole getUserRole(ChatUser user) {
        return users.stream().filter(x -> x.getUser().equals(user)).map(x -> ADMIN).findAny().orElseGet(() -> NONE);
    }

    @JsonProperty("type")
    private String getTypeForJSON() {
        return "contact";
    }

}
