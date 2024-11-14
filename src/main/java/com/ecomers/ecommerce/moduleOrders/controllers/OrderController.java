package com.ecomers.ecommerce.moduleOrders.controllers;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ecomers.ecommerce.Util.ApiResponse;
import com.ecomers.ecommerce.Util.Constants;
import com.ecomers.ecommerce.moduleOrders.DTO.OrderRequestDTO;
import com.ecomers.ecommerce.moduleOrders.models.Order;
import com.ecomers.ecommerce.moduleOrders.services.OrderService;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;


    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createOrder(@RequestBody OrderRequestDTO orderRequestDTO) {
        try {
            boolean randomOrder = orderRequestDTO.isRandomOrder();
            Order order = orderService.createOrder(orderRequestDTO.getCustomer(), orderRequestDTO.getOrderItemDTOs(), randomOrder);
            
            ApiResponse response = new ApiResponse(
                HttpStatus.CREATED.value(),   
                Constants.CREATE_ORDER,    
                order                        
            );
            return new ResponseEntity<>(response, HttpStatus.CREATED);  
        } catch (Exception e) {
            String errorMessage = "An error occurred while creating the order: " + e.getMessage();
            ApiResponse errorResponse = new ApiResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(), 
                errorMessage,                             
                null                                      
            );
            
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);  
        }
    }
    
    @GetMapping("/reports")
    public ResponseEntity<Map<String, Object>> getReports() {
        Map<String, Object> reports = orderService.reports();
        return ResponseEntity.ok(reports);
    }
}
