package com.uncledemy.salesmanagementsystem.service;

import com.uncledemy.salesmanagementsystem.dto.ProductDto;
import com.uncledemy.salesmanagementsystem.dto.ProductReportDto;
import com.uncledemy.salesmanagementsystem.exception.SalesManagementException;
import com.uncledemy.salesmanagementsystem.model.Product;

import java.util.List;

public interface ProductService {
    void createProduct(ProductDto productDto);
    boolean updateProduct(long productId, ProductDto productDto) throws SalesManagementException;
    Product findProductById(long productId) throws SalesManagementException;
    List<Product> getAllProduct();
    boolean deleteProduct(long productId) throws SalesManagementException;
    ProductReportDto generateProductReport();
}
