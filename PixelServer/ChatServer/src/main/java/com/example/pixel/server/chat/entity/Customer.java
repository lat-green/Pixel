package com.example.pixel.server.chat.entity;

import com.example.pixel.server.chat.entity.attachment.ChatUserAttachment;
import com.example.pixel.server.util.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "customer")
public class Customer implements BaseEntity {

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    private String username;

    @JsonIgnore
    @OneToMany(mappedBy = "user", orphanRemoval = true, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    private Set<ChatUserAttachment> chats;

    @Column(name = "created_date", nullable = false, updatable = false)
    private Date createdDate;

    @Column(name = "avatar", length = 1024 * 10)
    private String avatar;

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return id == customer.id;
    }

    @Override
    public String toString() {
        return "ChatUser{id=" + id + '}';
    }

}
