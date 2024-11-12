package com.ecomers.ecommerce.moduleOrders.models;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Discount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private double discountPercentage;  // El porcentaje de descuento
    private LocalDateTime startDate;    // Fecha de inicio del descuento
    private LocalDateTime endDate;      // Fecha de finalización
    private boolean randomOrder;
         public double getDiscountPercentage() {
            return discountPercentage;
        }
    
        public void setDiscountPercentage(double discountPercentage) {
            this.discountPercentage = discountPercentage;
        }
    
        // Getter y setter para startDate
        public LocalDateTime getStartDate() {
            return startDate;
        }
    
        public void setStartDate(LocalDateTime startDate) {
            this.startDate = startDate;
        }
    
        // Getter y setter para endDate
        public LocalDateTime getEndDate() {
            return endDate;
        }
    
        public void setEndDate(LocalDateTime endDate) {
            this.endDate = endDate;
        }
        public boolean isRandomOrder() {
            return randomOrder;
        }
        public void setRandomOrder(boolean randomOrder) {
            this.randomOrder = randomOrder;
        }
}
