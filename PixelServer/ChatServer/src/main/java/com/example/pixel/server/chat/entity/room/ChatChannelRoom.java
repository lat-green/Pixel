package com.example.pixel.server.chat.entity.room;

import com.example.pixel.server.chat.entity.attachment.ChatChannelUserAttachment;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.Set;

@Entity(name = "chat_channel_room")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatChannelRoom extends ChatRoom {

    @JsonIgnore
    @OneToMany(mappedBy = "channel", orphanRemoval = true, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    private Set<ChatChannelUserAttachment> users;

    @JsonProperty("type")
    private String getTypeForJSON() {
        return "channel";
    }

}
