package com.shopeasy.estore.productTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.shopeasy.estore.product.Product;
import com.shopeasy.estore.product.ProductRepository;
import com.shopeasy.estore.product.ProductService;
import com.shopeasy.estore.security.exception.ProductNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {
    @Mock
    private ProductRepository repository;
    @InjectMocks
    private ProductService service;
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetProductList() {
        // Mock the repository to return a list of products
        List<Product> productList = new ArrayList<>();
        when(repository.findAll()).thenReturn(productList);

        // Call the service method
        List<Product> result = service.getProductList();

        // Assert the result
        assertNotNull(result);
        assertEquals(0, result.size()); // Assuming the list is empty in this example
    }

    @Test
    void testGetProductById_ValidId() {
        Long validId = 1L;
        Product mockProduct = new Product();
        mockProduct.setId(validId);
        Optional<Product> optionalProduct = Optional.of(mockProduct);

        // Mock the repository to return a product when findById is called with a valid ID
        when(repository.findById(validId)).thenReturn(optionalProduct);

        // Call the service method
        Optional<Product> result = service.getProductById(validId);

        // Assert the result
        assertTrue(result.isPresent());
        assertEquals(validId, result.get().getId());
    }

    @Test
    void testGetProductById_InvalidId() {
        Long invalidId = null;
        // Call the service method with an invalid ID
        assertThrows(ProductNotFoundException.class, () -> service.getProductById(invalidId));
    }

    @Test
    void testSaveProduct() {
        // Create a sample product
        Product product = new Product();
        product.setId(1L);
        product.setProductName("Test Product");

        // Mock the repository to verify that saveProduct is called
        when(repository.save(any(Product.class))).thenReturn(product);

        // Call the service method
        service.saveProduct(product);

        // Verify that saveProduct was called once
        verify(repository, times(1)).save(any(Product.class));
    }

    @Test
    void testDeleteProduct() {
        Long productId = 1L;
        Product productToDelete = new Product();
        productToDelete.setId(productId);

        // Mock the repository to return the product to be deleted
        when(repository.findById(productId)).thenReturn(Optional.of(productToDelete));

        // Call the service method
        Optional<Product> result = service.deleteProduct(productId);

        // Verify that findById and deleteById were called once
        verify(repository, times(1)).findById(productId);
        verify(repository, times(1)).deleteById(productId);

        // Assert the result
        assertNotNull(result);
    }

}
