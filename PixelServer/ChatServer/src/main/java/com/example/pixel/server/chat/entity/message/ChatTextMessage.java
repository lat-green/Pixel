package com.example.pixel.server.chat.entity.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;

@Entity(name = "chat_text_message")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatTextMessage extends ChatMessage {

    @Column(nullable = false)
    private String content;

    @JsonProperty("type")
    private String getTypeForJSON() {
        return "text";
    }

}
