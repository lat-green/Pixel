package com.example.pixel.server.authorization.entity;

import com.example.pixel.server.util.entity.BaseEntity;
import com.example.pixel.server.util.entity.EntityAsNameOnlySerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Objects;
import java.util.Set;

@Entity(name = "client")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthClient implements BaseEntity {

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false, unique = true)
    private String clientId;

    @Column(nullable = false)
    private String clientSecret;

    @Column(nullable = false)
    private String clientName;

    @JsonSerialize(using = EntityAsNameOnlySerializer.class)
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<AuthClientScope> scopes;

    @JsonSerialize(using = EntityAsNameOnlySerializer.class)
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<AuthRedirectUri> redirectUris;

    @JsonSerialize(using = EntityAsNameOnlySerializer.class)
    @ManyToOne(optional = false, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @OnDelete(action = OnDeleteAction.CASCADE)
    private AuthUser owner;

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthClient other = (AuthClient) o;
        return Objects.equals(id, other.id);
    }

    @Override
    public String toString() {
        return "AuthClient(" + id + ")";
    }

    public AuthUser getOwner() {
        return owner;
    }

    public void setOwner(AuthUser authUsers) {
        this.owner = authUsers;
    }

}
