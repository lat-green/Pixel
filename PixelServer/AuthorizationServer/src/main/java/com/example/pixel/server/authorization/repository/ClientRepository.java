package com.example.pixel.server.authorization.repository;

import com.example.pixel.server.authorization.entity.AuthClient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<AuthClient, Long> {

    Optional<AuthClient> findByClientId(String clintId);

}
