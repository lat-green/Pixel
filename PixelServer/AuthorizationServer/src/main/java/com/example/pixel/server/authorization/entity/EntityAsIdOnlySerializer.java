package com.example.pixel.server.authorization.entity;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class EntityAsIdOnlySerializer extends StdSerializer<Object> {

    public EntityAsIdOnlySerializer() {
        this(null);
    }

    public EntityAsIdOnlySerializer(Class<Object> t) {
        super(t);
    }

    @Override
    public void serialize(Object entity, JsonGenerator generator, SerializerProvider provider) throws IOException {
        if (entity instanceof BaseEntity) {
            BaseEntity e = (BaseEntity) entity;
            generator.writeNumber(e.getId());
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
