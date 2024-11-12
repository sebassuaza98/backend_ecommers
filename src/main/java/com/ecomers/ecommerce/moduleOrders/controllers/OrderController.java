package com.ecomers.ecommerce.moduleOrders.controllers;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.ecomers.ecommerce.moduleOrders.DTO.OrderRequestDTO;
import com.ecomers.ecommerce.moduleOrders.models.Customer;
import com.ecomers.ecommerce.moduleOrders.models.Order;
import com.ecomers.ecommerce.moduleOrders.services.OrderService;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;


    @PostMapping("/create")
    public ResponseEntity<Order> createOrder(@RequestBody OrderRequestDTO orderRequestDTO) throws Exception {
        boolean randomOrder = orderRequestDTO.isRandomOrder();

        Order order = orderService.createOrder(orderRequestDTO.getCustomer(), orderRequestDTO.getOrderItemDTOs(), randomOrder);
        
        return ResponseEntity.ok(order);
    }
/*     @GetMapping("/top")
        public List<Customer> getTopCustomers(@RequestParam int limit) {
            return orderService.getTopCustomersByOrders(limit);
    }*/
    @GetMapping("/reports")
    public ResponseEntity<Map<String, Object>> getReports() {
        Map<String, Object> reports = orderService.reports();
        return ResponseEntity.ok(reports);
    }
}
