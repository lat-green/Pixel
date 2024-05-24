package com.example.pixel.server.chat.entity.chat;

import com.example.pixel.server.chat.entity.Customer;
import com.example.pixel.server.chat.entity.attachment.ChatChannelUserAttachment;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.Set;

import static com.example.pixel.server.chat.entity.chat.Chat.ChatRole.*;

@Entity(name = "chat_channel_room")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatChannel extends Chat {

    @JsonIgnore
    @OneToMany(mappedBy = "channel", orphanRemoval = true, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    private Set<ChatChannelUserAttachment> users;

    @Override
    public ChatRole getUserRole(Customer user) {
        return users.stream().filter(x -> x.getUser().equals(user)).map(x -> x.getRole()).map(x -> switch (x) {
            case PRIVATE_USER, PUBLIC_USER -> READ;
            case AUTHOR -> WRITE;
            case ADMIN -> ADMIN;
        }).findAny().orElseGet(() -> NONE);
    }

    @JsonProperty("type")
    private String getTypeForJSON() {
        return "channel";
    }

}
