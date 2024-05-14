package com.example.pixel.server.chat.entity.room;

import com.example.pixel.server.chat.entity.ChatUser;
import com.example.pixel.server.chat.entity.attachment.ChatGroupUserAttachment;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.Set;

import static com.example.pixel.server.chat.entity.room.ChatRoom.ChatRole.*;

@Entity(name = "chat_group_room")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatGroupRoom extends ChatRoom {

    @JsonIgnore
    @OneToMany(mappedBy = "group", orphanRemoval = true, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    private Set<ChatGroupUserAttachment> users;

    @Override
    public ChatRole getUserRole(ChatUser user) {
        return users.stream().filter(x -> x.getUser().equals(user)).map(x -> x.getRole()).map(x -> switch (x) {
            case USER -> WRITE;
            case ADMIN -> ADMIN;
        }).findAny().orElseGet(() -> NONE);
    }

    @JsonProperty("type")
    private String getTypeForJSON() {
        return "group";
    }

}
