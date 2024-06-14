package com.example.pixel.server.chat.entity.chat;

import com.example.pixel.server.chat.entity.Customer;
import com.example.pixel.server.chat.entity.attachment.GroupUserAttachment;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.Set;

import static com.example.pixel.server.chat.entity.chat.Chat.ChatRole.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "chat_group")
public class ChatGroup extends Chat {

    @JsonIgnore
    @OneToMany(mappedBy = "group", orphanRemoval = true, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    private Set<GroupUserAttachment> users;

    @Override
    public ChatRole getUserRole(Customer user) {
        return users.stream().filter(x -> x.getUser().equals(user)).map(x -> x.getRole()).map(x -> switch (x) {
            case USER -> WRITE;
            case ADMIN -> ADMIN;
        }).findAny().orElseGet(() -> NONE);
    }

    @Override
    public GroupUserAttachment getAttachment(Customer user) {
        return users.stream().filter(x -> x.getUser().equals(user)).findAny().get();
    }

    @JsonProperty("type")
    private String getTypeForJSON() {
        return "group";
    }

}
