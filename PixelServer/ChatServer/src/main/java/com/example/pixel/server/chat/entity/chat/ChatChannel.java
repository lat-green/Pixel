package com.example.pixel.server.chat.entity.chat;

import com.example.pixel.server.chat.entity.Customer;
import com.example.pixel.server.chat.entity.attachment.ChannelAttachment;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.List;

import static com.example.pixel.server.chat.entity.chat.Chat.ChatRole.*;

@Entity(name = "channel_chat")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatChannel extends Chat {

    @JsonIgnore
    @OneToMany(mappedBy = "channel", orphanRemoval = true, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    private List<ChannelAttachment> users;

    @Override
    public ChatRole getUserRole(Customer user) {
        return users.stream().filter(x -> x.getUser().equals(user)).map(x -> x.getRole()).map(x -> switch (x) {
            case PRIVATE_USER, PUBLIC_USER -> READ;
            case AUTHOR -> WRITE;
            case ADMIN -> ADMIN;
        }).findAny().orElseGet(() -> NONE);
    }

    @Override
    public ChannelAttachment getAttachment(Customer user) {
        return users.stream().filter(x -> x.getUser().equals(user)).findAny().get();
    }

    @JsonProperty("type")
    private String getTypeForJSON() {
        return "channel";
    }

}
