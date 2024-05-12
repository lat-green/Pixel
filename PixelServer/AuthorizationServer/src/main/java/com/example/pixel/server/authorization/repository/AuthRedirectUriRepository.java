package com.example.pixel.server.authorization.repository;

import com.example.pixel.server.authorization.entity.AuthRedirectUri;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthRedirectUriRepository extends JpaRepository<AuthRedirectUri, Long> {

    Optional<AuthRedirectUri> findByName(String name);

}
