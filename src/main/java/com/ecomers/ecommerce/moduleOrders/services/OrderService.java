package com.ecomers.ecommerce.moduleOrders.services;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecomers.ecommerce.Util.Constants;
import com.ecomers.ecommerce.exception.InsufficientStockException;
import com.ecomers.ecommerce.moduleOrders.DTO.OrderItemDTO;
import com.ecomers.ecommerce.moduleOrders.models.Customer;
import com.ecomers.ecommerce.moduleOrders.models.Discount;
import com.ecomers.ecommerce.moduleOrders.models.Order;
import com.ecomers.ecommerce.moduleOrders.models.OrderItem;
import com.ecomers.ecommerce.moduleOrders.repositories.CustomerRepository;
import com.ecomers.ecommerce.moduleOrders.repositories.DiscountRepository;
import com.ecomers.ecommerce.moduleOrders.repositories.OrderRepository;
import com.ecomers.ecommerce.moduleProducts.models.Product;
import com.ecomers.ecommerce.moduleProducts.respositories.ProductRepository;

import jakarta.transaction.Transactional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;  
    @Autowired
    private ProductRepository productRepository;  
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private DiscountRepository discountRepository; 
    
    public OrderService(OrderRepository orderRepository, CustomerRepository customerRepository) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
    }

    @Transactional
    public Order createOrder(Customer customerDTO, List<OrderItemDTO> orderItemDTOs, boolean randomOrder) throws Exception {
        Customer customer = getOrCreateCustomer(customerDTO);

        List<Customer> topCustomers = getTopCustomersByOrders(Constants.LIMIT);

        Order order = new Order();
        order.setCustomer(customer);
        order.setOrderDate(LocalDateTime.now());

        double totalAmount = calculateOrderTotalAmount(orderItemDTOs, order);

        LocalDateTime now = LocalDateTime.now();
        Optional<Discount> discountOpt = getDiscount(now, randomOrder);

        if (discountOpt.isPresent()) {
            totalAmount = applyDiscount(totalAmount, discountOpt.get());
        }

        if (topCustomers.contains(customer)) {
            totalAmount = applyTopCustomerDiscount(totalAmount);
        }

        order.setTotalAmount(totalAmount);
        return orderRepository.save(order);
    }

    public Customer getOrCreateCustomer(Customer customerDTO) {
        return customerRepository.findById(customerDTO.getId())
                .orElseGet(() -> {
                    Customer newCustomer = new Customer();
                    newCustomer.setId(customerDTO.getId());
                    newCustomer.setName(customerDTO.getName());
                    newCustomer.setEmail(customerDTO.getEmail());
                    return customerRepository.save(newCustomer);
                });
    }

    public double calculateOrderTotalAmount(List<OrderItemDTO> orderItemDTOs, Order order) throws Exception {
        double totalAmount = 0.0;

        for (OrderItemDTO orderItemDTO : orderItemDTOs) {
            Product product = productRepository.findById(orderItemDTO.getProductId())
                    .orElseThrow(() -> new Exception(Constants.PRODUCT_NOT + orderItemDTO.getProductId()));

            validateProductStock(product, orderItemDTO);

            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setQuantity(orderItemDTO.getQuantity());
            orderItem.setPrice(product.getPrice());
            
            double itemTotal = product.getPrice() * orderItemDTO.getQuantity();
            totalAmount += itemTotal;
            
            orderItem.setOrder(order);
            order.getItems().add(orderItem);

            product.setStock(product.getStock() - orderItemDTO.getQuantity());
            productRepository.save(product);
        }

        return totalAmount;
    }

    public void validateProductStock(Product product, OrderItemDTO orderItemDTO) {
        if (product.getStock() < orderItemDTO.getQuantity()) {
            throw new InsufficientStockException(Constants.EXCCESDS + orderItemDTO.getProductId());
        }
    }

    public Optional<Discount> getDiscount(LocalDateTime now, boolean randomOrder) {
        if (randomOrder) {
            return discountRepository.findByStartDateBeforeAndEndDateAfterAndRandomOrder(now, now, true);
        } else {
            return discountRepository.findByStartDateBeforeAndEndDateAfterAndRandomOrder(now, now, false);
        }
    }

    public double applyDiscount(double totalAmount, Discount discount) {
        double discountPercentage = discount.getDiscountPercentage();
        double discountAmount = totalAmount * (discountPercentage / 100);
        return totalAmount - discountAmount;
    }

    public double applyTopCustomerDiscount(double totalAmount) {
        double additionalDiscountAmount = totalAmount * Constants.DESCOUNT;
        return totalAmount - additionalDiscountAmount;
    }

    public List<Customer> getTopCustomersByOrders(int limit) {
        Pageable pageable = PageRequest.of(0, limit);
        List<Object[]> results = orderRepository.findTopCustomersByOrders(pageable);

        List<Customer> topCustomers = new ArrayList<>();
        for (Object[] result : results) {
            Customer customer = (Customer) result[0];
            topCustomers.add(customer);
        }
        return topCustomers;
    }

    public Map<String, Object> reports() {
        Map<String, Object> reportData = new HashMap<>();
        List<Product> activeProducts = productRepository.findByActivoTrue();
        reportData.put("activeProducts", activeProducts);

        Pageable top5 = PageRequest.of(0, 5);
        List<Object[]> topProductsResults = orderRepository.findTopProductsBySales(top5);
        List<Product> topProducts = new ArrayList<>();
        for (Object[] result : topProductsResults) {
            Product product = (Product) result[0];
            topProducts.add(product);
        }
        reportData.put("topSellingProducts", topProducts);
        List<Customer> topCustomers = getTopCustomersByOrders(5);
        reportData.put("topCustomers", topCustomers);

        return reportData;
    }


}