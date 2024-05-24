package com.example.pixel.server.chat.serializer;

import com.example.pixel.server.chat.entity.attachment.ChatChannelUserAttachment;
import com.example.pixel.server.chat.entity.attachment.ChatContactUserAttachment;
import com.example.pixel.server.chat.entity.attachment.ChatUserAttachment;
import com.example.pixel.server.chat.entity.attachment.GroupUserAttachment;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

@Component
public class ChatUserAttachmentToRoomSerializer extends StdSerializer<ChatUserAttachment> {

    public ChatUserAttachmentToRoomSerializer() {
        this(ChatUserAttachment.class);
    }

    public ChatUserAttachmentToRoomSerializer(Class<ChatUserAttachment> t) {
        super(t);
    }

    @Override
    public void serialize(ChatUserAttachment entity, JsonGenerator generator, SerializerProvider provider) throws IOException {
        if (entity instanceof GroupUserAttachment e) {
            generator.writeStartObject();
            generator.writeNumberField("user", e.getUser().getId());
            generator.writeStringField("role", e.getRole().name());
            generator.writeEndObject();
            return;
        }
        if (entity instanceof ChatChannelUserAttachment e) {
            generator.writeStartObject();
            generator.writeNumberField("user", e.getUser().getId());
            generator.writeStringField("role", e.getRole().name());
            generator.writeEndObject();
            return;
        }
        if (entity instanceof ChatContactUserAttachment e) {
            generator.writeStartObject();
            generator.writeNumberField("user", e.getUser().getId());
            generator.writeEndObject();
            return;
        }
        throw new UnsupportedEncodingException("class: " + entity.getClass().getName());
    }

}
