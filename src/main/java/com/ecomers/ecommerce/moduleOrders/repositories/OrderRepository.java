package com.ecomers.ecommerce.moduleOrders.repositories;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ecomers.ecommerce.moduleOrders.models.Order;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    
    @Query("SELECT o.customer, COUNT(o) AS orderCount FROM Order o GROUP BY o.customer ORDER BY orderCount DESC")
    List<Object[]> findTopCustomersByOrders(Pageable pageable);
    
    @Query("SELECT oi.product, SUM(oi.quantity) AS totalQuantity " +
           "FROM OrderItem oi " +
           "GROUP BY oi.product " +
           "ORDER BY totalQuantity DESC")
    List<Object[]> findTopProductsBySales(Pageable pageable);
}