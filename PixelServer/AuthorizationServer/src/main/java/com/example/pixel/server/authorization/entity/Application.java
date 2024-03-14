package com.example.pixel.server.authorization.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Application implements BaseEntity {

    @Id
    @GeneratedValue
    long id;

    @JsonSerialize(using = EntityAsIdOnlySerializer.class)
    @ManyToOne(optional = false, cascade = CascadeType.PERSIST)
    private User owner;

}
