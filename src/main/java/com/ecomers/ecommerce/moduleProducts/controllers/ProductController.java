package com.ecomers.ecommerce.moduleProducts.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ecomers.ecommerce.Util.ApiResponse;
import com.ecomers.ecommerce.Util.Constants;
import com.ecomers.ecommerce.exception.ResourceNotFoundException;
import com.ecomers.ecommerce.moduleProducts.models.Product;
import com.ecomers.ecommerce.moduleProducts.services.ProductService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }
    @PostMapping("/products")
    public ResponseEntity<ApiResponse> createProduct(@RequestBody Product product) {
        try {
            Product createdProduct = productService.createProduct(product);
            ApiResponse response = new ApiResponse(
                HttpStatus.CREATED.value(),
                Constants.CREATE_PRODUCT,
                createdProduct
            );
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            ApiResponse errorResponse = new ApiResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                Constants.ERROR_PRODUCT,
                null
            );
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping(("/getProducts"))
    public ResponseEntity<ApiResponse> getAllProducts() {
        try {
            List<Product> products = productService.findAllProducts();
            ApiResponse response = new ApiResponse(
                HttpStatus.OK.value(),
                    Constants.SUCCESS_PRODUCT,
                products
            );
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            ApiResponse errorResponse = new ApiResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    Constants.ERROR_PRODUCT,
                null
            );
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("update")
    public ResponseEntity<ApiResponse> updateProduct(@RequestBody Product product) {
        try {
            Product updatedProduct = productService.updateProduct(product);

            ApiResponse response = new ApiResponse(
                HttpStatus.OK.value(),
                    Constants.UPDATE_PRODUCT,
                updatedProduct
            );
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            ApiResponse errorResponse = new ApiResponse(
                HttpStatus.NOT_FOUND.value(),
                e.getMessage(),
                null
            );
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            ApiResponse errorResponse = new ApiResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    Constants.ERROR_UPDATE,
                null
            );
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/produtsActive")
    public ResponseEntity<List<Product>> getActiveProducts() {
        List<Product> activeProducts = productService.getActiveProducts();
        return new ResponseEntity<>(activeProducts, HttpStatus.OK);
    }

}
