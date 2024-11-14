package com.ecomers.ecommerce.moduleOrders.DTO;

import java.util.List;

import com.ecomers.ecommerce.moduleOrders.models.Customer;

public class OrderRequestDTO {
    private Customer customer;
    private List<OrderItemDTO> orderItemDTOs;  
    private boolean randomOrder;
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<OrderItemDTO> getOrderItemDTOs() {
        return orderItemDTOs;
    }

    public void setOrderItemDTOs(List<OrderItemDTO> orderItemDTOs) {
        this.orderItemDTOs = orderItemDTOs;
    }
    public boolean isRandomOrder() {
        return randomOrder;
    }
    public void setRandomOrder(boolean randomOrder) {
        this.randomOrder = randomOrder;
    }
}
