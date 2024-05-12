package com.example.pixel.server.authorization.repository;

import com.example.pixel.server.authorization.entity.AuthClientScope;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientScopeRepository extends JpaRepository<AuthClientScope, Long> {

    Optional<AuthClientScope> findByName(String name);

}
