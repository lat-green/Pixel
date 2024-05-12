package com.example.pixel.server.util.entity;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class EntityAsNameOnlySerializer extends StdSerializer<Object> {

    public EntityAsNameOnlySerializer() {
        this(null);
    }

    public EntityAsNameOnlySerializer(Class<Object> t) {
        super(t);
    }

    @Override
    public void serialize(Object entity, JsonGenerator generator, SerializerProvider provider) throws IOException {
        if (entity instanceof BaseNameEntity e) {
            generator.writeString(e.getName());
            return;
        }
        if (entity instanceof Iterable<?> c) {
            generator.writeStartArray();
            for (var e : c) {
                serialize(e, generator, provider);
            }
            generator.writeEndArray();
            return;
        }
        throw new UnsupportedEncodingException("class: " + entity.getClass().getName());
    }

}
