package com.example.pixel.server.chat.entity.chat;

import com.example.pixel.server.chat.entity.Customer;
import com.example.pixel.server.chat.entity.attachment.ChatChannelUserAttachment;
import com.example.pixel.server.chat.entity.attachment.ChatContactUserAttachment;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.Set;

import static com.example.pixel.server.chat.entity.chat.Chat.ChatRole.ADMIN;
import static com.example.pixel.server.chat.entity.chat.Chat.ChatRole.NONE;

@Entity(name = "chat_contact_room")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatContact extends Chat {

    @JsonIgnore
    @OneToMany(mappedBy = "contact", orphanRemoval = true, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    private Set<ChatContactUserAttachment> users;

    @Override
    public ChatRole getUserRole(Customer user) {
        return users.stream().filter(x -> x.getUser().equals(user)).map(x -> ADMIN).findAny().orElseGet(() -> NONE);
    }

    @Override
    public ChatContactUserAttachment getAttachment(Customer user) {
        return users.stream().filter(x -> x.getUser().equals(user)).findAny().get();
    }

    @JsonProperty("type")
    private String getTypeForJSON() {
        return "contact";
    }

}
