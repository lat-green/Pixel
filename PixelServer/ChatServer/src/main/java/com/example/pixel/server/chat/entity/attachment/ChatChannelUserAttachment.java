package com.example.pixel.server.chat.entity.attachment;

import com.example.pixel.server.chat.entity.room.ChatChannelRoom;
import com.example.pixel.server.util.entity.EntityAsIdOnlySerializer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity(name = "group_channel_attachment")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(
        uniqueConstraints = @UniqueConstraint(columnNames = {"user", "channel"})
)
public class ChatChannelUserAttachment extends ChatUserAttachment {

    @JsonSerialize(using = EntityAsIdOnlySerializer.class)
    @ManyToOne(optional = false, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @OnDelete(action = OnDeleteAction.CASCADE)
    private ChatChannelRoom channel;

    @Column(nullable = false)
    private ChatChannelRole role;

    @JsonProperty("type")
    private String getTypeForJSON() {
        return "channel";
    }

    public enum ChatChannelRole {

        ADMIN, AUTHOR, PUBLIC_USER, PRIVATE_USER

    }

}
