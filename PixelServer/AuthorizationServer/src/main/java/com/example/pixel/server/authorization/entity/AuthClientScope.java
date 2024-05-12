package com.example.pixel.server.authorization.entity;

import com.example.pixel.server.util.entity.BaseEntity;
import com.example.pixel.server.util.entity.BaseNameEntity;
import com.example.pixel.server.util.entity.EntityAsIdOnlySerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Objects;

@Entity(name = "scope")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthClientScope implements BaseEntity, BaseNameEntity {

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthClientScope other = (AuthClientScope) o;
        return Objects.equals(id, other.id);
    }

    @Override
    public String toString() {
        return "AuthClientScope(" + name + ")";
    }

}
