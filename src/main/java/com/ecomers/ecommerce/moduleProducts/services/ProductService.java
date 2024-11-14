package com.ecomers.ecommerce.moduleProducts.services;

import org.springframework.stereotype.Service;
import com.ecomers.ecommerce.Util.Constants;
import com.ecomers.ecommerce.exception.ResourceNotFoundException;
import com.ecomers.ecommerce.moduleProducts.models.Product;
import com.ecomers.ecommerce.moduleProducts.respositories.ProductRepository;


import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    public Product createProduct(Product product) {
        product.setActivo(true);
        return productRepository.save(product);
    }
    
    public Product updateProduct(Product product) {
        Long id = product.getId();
        Product existingProduct = productRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException(Constants.PRODUCT_NOT + id));
        existingProduct.setName(product.getName());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setStock(product.getStock());
        existingProduct.setActivo(product.getActivo());
    
        return productRepository.save(existingProduct);
    }
    
    public List<Product> getActiveProducts() {
        return productRepository.findByActivoTrue();
    }

}