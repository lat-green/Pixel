package com.example.pixel.server.authorization.entity;

import com.example.pixel.server.util.entity.BaseEntity;
import com.example.pixel.server.util.entity.EntityAsNameOnlySerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Entity(name = "customer")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthUser implements BaseEntity, UserDetails {

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false, unique = true)
    private String username;

    @JsonIgnore
    @Column(nullable = false)
    private String password;

    @Column(name = "created_date", nullable = false, updatable = false)
    private Date createdDate = new Date();

    @JsonSerialize(using = EntityAsNameOnlySerializer.class)
    @OneToMany(mappedBy = "owner", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH}, orphanRemoval = true)
    private Set<AuthClient> clients;

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthUser other = (AuthUser) o;
        return Objects.equals(id, other.id);
    }

    @Override
    public String toString() {
        return "AuthUser(" + id + ")";
    }

}
