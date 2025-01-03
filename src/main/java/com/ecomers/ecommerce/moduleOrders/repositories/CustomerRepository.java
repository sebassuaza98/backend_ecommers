package com.ecomers.ecommerce.moduleOrders.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecomers.ecommerce.moduleOrders.models.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
