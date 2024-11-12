package com.ecomers.ecommerce.moduleOrders.repositories;

import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.ecomers.ecommerce.moduleOrders.models.Discount;

public interface DiscountRepository extends JpaRepository<Discount, Long> {
    Optional<Discount> findByStartDateBeforeAndEndDateAfterAndRandomOrder(LocalDateTime date1, LocalDateTime date2, boolean randomOrder);

}