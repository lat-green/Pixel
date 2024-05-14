package com.example.pixel.server.chat.entity.attachment;

import com.example.pixel.server.chat.entity.room.ChatGroupRoom;
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

@Entity(name = "group_user_attachment")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(
        uniqueConstraints = @UniqueConstraint(columnNames = {"user", "group"})
)
public class ChatGroupUserAttachment extends ChatUserAttachment {

    @JsonSerialize(using = EntityAsIdOnlySerializer.class)
    @ManyToOne(optional = false, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @OnDelete(action = OnDeleteAction.CASCADE)
    private ChatGroupRoom group;

    @Column(nullable = false)
    private ChatGroupRole role;

    @JsonProperty("type")
    private String getTypeForJSON() {
        return "group";
    }

    public enum ChatGroupRole {

        ADMIN, USER

    }

}
