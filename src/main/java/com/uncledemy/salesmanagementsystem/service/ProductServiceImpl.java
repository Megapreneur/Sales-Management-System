package com.uncledemy.salesmanagementsystem.service;

import com.uncledemy.salesmanagementsystem.dto.*;
import com.uncledemy.salesmanagementsystem.exception.SalesManagementException;
import com.uncledemy.salesmanagementsystem.model.Product;
import com.uncledemy.salesmanagementsystem.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{
    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    private final ProductRepository productRepository;
    @Override
    public void createProduct(ProductDto productDto) {
        Product newProduct = new Product();
        newProduct.setName(productDto.getName());
        newProduct.setCreationDate(LocalDateTime.now());
        newProduct.setDescription(productDto.getDescription());
        newProduct.setPrice(productDto.getPrice());
        newProduct.setAvailableQuantity(productDto.getAvailableQuantity());
        newProduct.setCategory(productDto.getCategory());
        newProduct.setTotalRevenue(BigDecimal.ZERO);
        logger.info("The Product {} was registered by a Staff.", newProduct.getName());

        productRepository.save(newProduct);
    }

    @Override
    public boolean updateProduct(long productId, ProductDto productDto) throws SalesManagementException {
        Product savedProduct = productRepository.findById(productId).orElseThrow(()->
                new SalesManagementException("Product with ID " + productId + " does not exist"));
        savedProduct.setName(productDto.getName());
        savedProduct.setCreationDate(LocalDateTime.now());
        savedProduct.setDescription(productDto.getDescription());
        savedProduct.setPrice(productDto.getPrice());
        savedProduct.setAvailableQuantity(productDto.getAvailableQuantity());
        savedProduct.setCategory(productDto.getCategory());
        productRepository.save(savedProduct);
        logger.info("Product with ID: {} was updated", productId);

        return true;


    }

    @Override
    public Product findProductById(long productId) throws SalesManagementException {
        return productRepository.findById(productId).orElseThrow(()->
                new SalesManagementException("Product with ID " + productId + " does not exist"));
    }

    @Override
    public List<Product> getAllProduct() {
        return productRepository.findAll();
    }

    @Override
    public boolean deleteProduct(long productId) throws SalesManagementException {
        Product product = findProductById(productId);
        logger.info("The Product: {} is deleted.", product.getName());
        productRepository.delete(product);
        return true;
    }


    @Override
    public ProductReportDto generateProductReport() {
        ProductReportDto productReport = new ProductReportDto();

        try {
            List<ProductInventoryDto> inventoryStatus = productRepository.findAll().stream()
                    .map(product -> {
                        ProductInventoryDto inventoryDTO = new ProductInventoryDto();
                        inventoryDTO.setProductId(product.getId());
                        inventoryDTO.setProductName(product.getName());
                        inventoryDTO.setAvailableQuantity(product.getAvailableQuantity());
                        return inventoryDTO;
                    })
                    .collect(Collectors.toList());
            productReport.setInventoryStatus(inventoryStatus);

            List<ProductSalesDto> salesPerformance = productRepository.findAll().stream()
                    .map(product -> {
                        ProductSalesDto salesDTO = new ProductSalesDto();
                        salesDTO.setProductId(product.getId());
                        salesDTO.setProductName(product.getName());

                        int totalUnitsSold = productRepository.getTotalUnitsSoldById(product.getId());
                        BigDecimal totalRevenue = productRepository.getTotalRevenueById(product.getId());

                        salesDTO.setTotalUnitsSold(totalUnitsSold);
                        salesDTO.setTotalRevenue(totalRevenue);

                        return salesDTO;
                    })
                    .collect(Collectors.toList());
            productReport.setSalesPerformance(salesPerformance);

            List<BigDecimal> prices = productRepository.findAll().stream()
                    .map(Product::getPrice)
                    .toList();
            BigDecimal averagePrice = prices.stream()
                    .reduce(BigDecimal.ZERO, BigDecimal::add)
                    .divide(BigDecimal.valueOf(prices.size()), 2, RoundingMode.HALF_UP);
            BigDecimal minPrice = prices.stream()
                    .min(BigDecimal::compareTo)
                    .orElse(BigDecimal.ZERO);
            BigDecimal maxPrice = prices.stream()
                    .max(BigDecimal::compareTo)
                    .orElse(BigDecimal.ZERO);
            PricingAnalysisDto pricingAnalysis = new PricingAnalysisDto();
            pricingAnalysis.setAveragePrice(averagePrice);
            pricingAnalysis.setMinPrice(minPrice);
            pricingAnalysis.setMaxPrice(maxPrice);
            productReport.setPricingAnalysis(pricingAnalysis);

            logger.info("Product Report was generated and viewed by an ADMIN");
        } catch (Exception e) {
            logger.error("Error generating product report: {}", e.getMessage());
        }

        return productReport;
    }



}
