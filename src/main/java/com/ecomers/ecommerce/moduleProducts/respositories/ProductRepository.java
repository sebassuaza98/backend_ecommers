package com.ecomers.ecommerce.moduleProducts.respositories;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ecomers.ecommerce.moduleProducts.models.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByActivoTrue();
}