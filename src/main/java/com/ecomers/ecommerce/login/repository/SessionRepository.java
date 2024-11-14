package com.ecomers.ecommerce.login.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecomers.ecommerce.login.models.Session;

public interface SessionRepository extends JpaRepository<Session, Long> {
    Session findByToken(String token);
}
