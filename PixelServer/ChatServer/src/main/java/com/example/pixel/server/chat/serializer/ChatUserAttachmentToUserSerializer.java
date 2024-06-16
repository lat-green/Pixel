package com.example.pixel.server.chat.serializer;

import com.example.pixel.server.chat.entity.attachment.ChannelAttachment;
import com.example.pixel.server.chat.entity.attachment.ChatAttachment;
import com.example.pixel.server.chat.entity.attachment.GroupAttachment;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

@Component
public class ChatUserAttachmentToUserSerializer extends StdSerializer<ChatAttachment> {

    public ChatUserAttachmentToUserSerializer() {
        this(ChatAttachment.class);
    }

    public ChatUserAttachmentToUserSerializer(Class<ChatAttachment> t) {
        super(t);
    }

    @Override
    public void serialize(ChatAttachment entity, JsonGenerator generator, SerializerProvider provider) throws IOException {
        if (entity instanceof GroupAttachment e) {
            generator.writeStartObject();
            generator.writeNumberField("id", e.getGroup().getId());
            generator.writeStringField("role", e.getRole().name());
            generator.writeStringField("type", "group");
            generator.writeEndObject();
            return;
        }
        if (entity instanceof ChannelAttachment e) {
            generator.writeStartObject();
            generator.writeNumberField("id", e.getChannel().getId());
            generator.writeStringField("role", e.getRole().name());
            generator.writeStringField("type", "channel");
            generator.writeEndObject();
            return;
        }
        throw new UnsupportedEncodingException("class: " + entity.getClass().getName());
    }

}
