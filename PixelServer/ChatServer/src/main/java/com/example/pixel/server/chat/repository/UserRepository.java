package com.example.pixel.server.chat.repository;

import com.example.pixel.server.chat.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByUsername(String username);

}
