package com.ecomers.ecommerce.login.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecomers.ecommerce.login.models.Users;

public interface UserRepository extends JpaRepository<Users, Long> {
}
