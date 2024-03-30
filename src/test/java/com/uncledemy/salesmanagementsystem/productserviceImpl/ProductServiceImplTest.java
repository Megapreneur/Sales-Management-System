package com.uncledemy.salesmanagementsystem.productserviceImpl;

import com.uncledemy.salesmanagementsystem.dto.ProductDto;
import com.uncledemy.salesmanagementsystem.exception.SalesManagementException;
import com.uncledemy.salesmanagementsystem.model.Product;
import com.uncledemy.salesmanagementsystem.repository.ProductRepository;
import com.uncledemy.salesmanagementsystem.service.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    private final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void createProduct_ValidProduct_Success() {
        ProductDto productDto = new ProductDto();
        productDto.setName("Test Product");
        productDto.setDescription("Test Description");
        productDto.setPrice(BigDecimal.valueOf(100));
        productDto.setAvailableQuantity(10);
        productDto.setCategory("Test Category");
        assertDoesNotThrow(() -> productService.createProduct(productDto));
    }

    @Test
    void updateProduct_ValidProductId_Success() throws SalesManagementException {
        long productId = 1L;
        Product product = new Product();
        product.setId(productId);
        ProductDto productDto = new ProductDto();
        productDto.setName("Updated Product");
        productDto.setDescription("Updated Description");
        productDto.setPrice(BigDecimal.valueOf(200));
        productDto.setAvailableQuantity(20);
        productDto.setCategory("Updated Category");
        when(productRepository.findById(productId)).thenReturn(java.util.Optional.of(product));
        assertTrue(productService.updateProduct(productId, productDto));
    }

    @Test
    void findProductById_ValidProductId_ReturnsProduct() throws SalesManagementException {
        long productId = 1L;
        Product product = new Product();
        product.setId(productId);
        when(productRepository.findById(productId)).thenReturn(java.util.Optional.of(product));
        assertNotNull(productService.findProductById(productId));
    }

    @Test
    void getAllProduct_ReturnsListOfProducts() {
        List<Product> products = new ArrayList<>();
        products.add(new Product());
        when(productRepository.findAll()).thenReturn(products);
        assertEquals(products, productService.getAllProduct());
    }

    @Test
    void deleteProduct_ValidProductId_Success() throws SalesManagementException {
        long productId = 1L;
        Product product = new Product();
        product.setId(productId);
        when(productRepository.findById(productId)).thenReturn(java.util.Optional.of(product));
        assertTrue(productService.deleteProduct(productId));
    }


}
