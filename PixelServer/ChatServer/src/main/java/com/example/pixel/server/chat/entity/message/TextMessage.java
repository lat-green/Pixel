package com.example.pixel.server.chat.entity.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "chat_text_message")
public class TextMessage extends Message {

    @Column(nullable = false)
    private String content;

    @JsonProperty("type")
    private String getTypeForJSON() {
        return "text";
    }

}