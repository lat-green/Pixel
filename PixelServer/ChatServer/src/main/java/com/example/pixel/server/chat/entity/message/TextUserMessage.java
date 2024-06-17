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
@Entity(name = "text_message")
public class TextUserMessage extends UserMessage {

    @Column(nullable = false, length = 4 * 1024)
    private String content;

    @JsonProperty("type")
    private String getTypeForJSON() {
        return "text";
    }

}
