package com.ecomers.ecommerce;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.ecomers.ecommerce.Util.Constants;
import com.ecomers.ecommerce.exception.ResourceNotFoundException;
import com.ecomers.ecommerce.moduleProducts.controllers.ProductController;
import com.ecomers.ecommerce.moduleProducts.models.Product;
import com.ecomers.ecommerce.moduleProducts.services.ProductService;

@ExtendWith(MockitoExtension.class)
public class ProductControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    private Product product;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
        product = new Product();
        product.setId(121L);
        product.setName("Producto");
        product.setPrice(100.0);
    }

    @Test
    public void testCreateProduct() throws Exception {
        when(productService.createProduct(any(Product.class))).thenReturn(product);

        mockMvc.perform(post("/api/products")
                .contentType("application/json")
                .content("{\"name\":\"Producto\", \"price\":100.0}")
        )
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.status").value(HttpStatus.CREATED.value()))
        .andExpect(jsonPath("$.message").value(Constants.CREATE_PRODUCT))
        .andExpect(jsonPath("$.data.name").value("Producto"))
        .andExpect(jsonPath("$.data.price").value(100.0));
    }

    @Test
    public void testGetAllProducts() throws Exception {
        List<Product> products = List.of(product);
        when(productService.findAllProducts()).thenReturn(products);

        mockMvc.perform(get("/api/getProducts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.message").value(Constants.SUCCESS_PRODUCT))
                .andExpect(jsonPath("$.data[0].name").value("Producto"))
                .andExpect(jsonPath("$.data[0].price").value(100.0));
    }

    @Test
    public void testUpdateProduct() throws Exception {
        // Verifica que el objeto mockeado tenga los valores correctos
        Product updatedProduct = new Product();
        updatedProduct.setId(1L);
        updatedProduct.setName("Updated Product Name");
        updatedProduct.setPrice(150.0);

        when(productService.updateProduct(any(Product.class))).thenReturn(updatedProduct);

        mockMvc.perform(put("/api/update")
                .contentType("application/json")
                .content("{\"id\":\"1\", \"name\":\"Updated Product Name\", \"price\":150.0}")
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.status").value(HttpStatus.OK.value()))
        .andExpect(jsonPath("$.message").value(Constants.UPDATE_PRODUCT))
        .andExpect(jsonPath("$.data.name").value("Updated Product Name"))
        .andExpect(jsonPath("$.data.price").value(150.0));
    }

    @Test
    public void testUpdateProductNotFound() throws Exception {
        when(productService.updateProduct(any(Product.class))).thenThrow(new ResourceNotFoundException("Product not found"));

        mockMvc.perform(put("/api/update")
                .contentType("application/json")
                .content("{\"id\":\"1\", \"name\":\"Updated Product Name\", \"price\":150.0}")
        )
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.status").value(HttpStatus.NOT_FOUND.value()))
        .andExpect(jsonPath("$.message").value("Product not found"));
    }
   
}
