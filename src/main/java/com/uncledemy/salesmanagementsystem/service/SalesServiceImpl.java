package com.uncledemy.salesmanagementsystem.service;

import com.uncledemy.salesmanagementsystem.dto.*;
import com.uncledemy.salesmanagementsystem.exception.SalesManagementException;
import com.uncledemy.salesmanagementsystem.model.Client;
import com.uncledemy.salesmanagementsystem.model.Product;
import com.uncledemy.salesmanagementsystem.model.Sales;
import com.uncledemy.salesmanagementsystem.model.User;
import com.uncledemy.salesmanagementsystem.repository.ClientRepository;
import com.uncledemy.salesmanagementsystem.repository.ProductRepository;
import com.uncledemy.salesmanagementsystem.repository.SalesRepository;
import com.uncledemy.salesmanagementsystem.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SalesServiceImpl implements SalesService{
    private static final Logger logger = LoggerFactory.getLogger(SalesManagementException.class);

    private final SalesRepository salesRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final ClientRepository clientRepository;


    @Transactional
    @Override
    public boolean createSales(Long userId, SalesDto salesDto) throws SalesManagementException {
        User savedUser = userRepository.findById(userId).orElseThrow(()->
                new UsernameNotFoundException("Invalid User"));
        Client savedClient = clientRepository.findByPhoneNumber(salesDto.getPhoneNumber()).orElseThrow(()->
                new UsernameNotFoundException("This Client is yet to be registered"));
        BigDecimal totalBill = BigDecimal.ZERO;
        Set<Product> products = new HashSet<>();
        for (Map.Entry<Long, Integer> entry : salesDto.getProductQuantities().entrySet()) {
            Long productId = entry.getKey();
            Integer quantity = entry.getValue();

            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new SalesManagementException("Product not found with ID: " + productId));

            if (product.getAvailableQuantity() >= quantity && quantity > 0) {
                BigDecimal productTotal = product.getPrice().multiply(BigDecimal.valueOf(quantity));
                totalBill = totalBill.add(productTotal);

                product.setAvailableQuantity(product.getAvailableQuantity() - quantity);
                product.setTotalUnitsSoldBy(product.getTotalUnitsSoldBy() + quantity);
                product.setTotalRevenue(product.getTotalRevenue().add(product.getPrice().multiply(BigDecimal.valueOf(quantity))));
                productRepository.save(product);

                products.add(product);
            } else {
                throw new SalesManagementException("Insufficient quantity for product with ID: " + productId);
            }
        }
        Sales newSales = new Sales();
        newSales.setSeller(savedUser);
        newSales.setClient(savedClient);
        newSales.setQuantity(salesDto.getTotalQuantity());
        newSales.setTotalAmount(totalBill);
        newSales.setProducts(products);
        newSales.setCreationDate(LocalDateTime.now());
        savedClient.setTotalSpent(savedClient.getTotalSpent().add(totalBill));
        savedClient.setLastPurchaseDate(LocalDateTime.now());
        salesRepository.save(newSales);
        clientRepository.save(savedClient);
        logger.info("A new Sales was created by a Staff");

        return true;
    }

    @Override
    public boolean updateSales(long salesId, long userId, SalesUpdateDto salesUpdateDto) throws SalesManagementException {
        Sales savedSales = salesRepository.findById(salesId).orElseThrow(()->
                new SalesManagementException("This transaction does not exist"));
        savedSales.setQuantity(salesUpdateDto.getQuantity());
        savedSales.setTotalAmount(salesUpdateDto.getTotalAmount());
        salesRepository.save(savedSales);
        logger.info("The Sales was ID : {} was updated", salesId);

        return true;
    }

    @Override
    public Sales findSalesById(long salesId) throws SalesManagementException {
        return salesRepository.findById(salesId).orElseThrow(()->
                new SalesManagementException("Transaction with the ID does not exist"));
    }
    @Override
    public List<Sales> getAllSales() {
        return salesRepository.findAll();
    }
    @Override
    public SalesReportDto generateSalesReport(String startDate, String endDate) {
        LocalDate start = LocalDate.parse(startDate, DateTimeFormatter.ISO_DATE);
        LocalDate end = LocalDate.parse(endDate, DateTimeFormatter.ISO_DATE);
        List<Sales> sales = salesRepository.findAll().stream().filter(transaction ->
                transaction.getCreationDate().isEqual(start.atStartOfDay())).filter(transaction ->
                transaction.getCreationDate().isEqual(end.atStartOfDay())).toList();

//        List<Sales> sales = salesRepository.findAll().stream()
//                .filter(transaction -> !transaction.getCreationDate().isBefore(start.atStartOfDay())
//                        && !transaction.getCreationDate().isAfter(end.atStartOfDay()))
//                .collect(Collectors.toList());


        int totalSales = sales.size();
        logger.info("The total number of sales is : {}",totalSales);
        BigDecimal totalRevenue = calculateTotalRevenue(sales);
        List<TopProductDto> topSellingProducts = calculateTopSellingProducts(sales);
        List<TopSellerDto> topPerformingSellers = calculateTopPerformingSellers(sales);

        SalesReportDto salesReport = new SalesReportDto();
        salesReport.setTotalSales(totalSales);
        salesReport.setTotalRevenue(totalRevenue);
        salesReport.setTopSellingProducts(topSellingProducts);
        salesReport.setTopPerformingSellers(topPerformingSellers);
        logger.info("Sales Report was generated and viewed by an ADMIN");
        return salesReport;
    }

    private BigDecimal calculateTotalRevenue(List<Sales> sales) {
        return sales.stream()
                .map(Sales::getTotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    private List<TopProductDto> calculateTopSellingProducts(List<Sales> sales) {
        return sales.stream()
                .flatMap(sale -> sale.getProducts().stream())
                .collect(Collectors.groupingBy(Product::getId, Collectors.summingInt(Product::getTotalUnitsSoldBy)))
                .entrySet().stream()
                .map(entry -> {
                    Product product = productRepository.findById(entry.getKey()).orElse(null);
                    if (product != null) {
                        TopProductDto dto = new TopProductDto();
                        dto.setProductId(product.getId());
                        dto.setProductName(product.getName());
                        dto.setUnitsSold(entry.getValue());
                        dto.setRevenue(product.getPrice().multiply(BigDecimal.valueOf(entry.getValue())));
                        return dto;
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .sorted((p1, p2) -> p2.getUnitsSold() - p1.getUnitsSold())
                .limit(10)
                .toList();
    }

    private List<TopSellerDto> calculateTopPerformingSellers(List<Sales> sales) {
        Map<User, BigDecimal> sellerRevenueMap = sales.stream()
                .collect(Collectors.groupingBy(Sales::getSeller, Collectors.reducing(BigDecimal.ZERO, Sales::getTotalAmount, BigDecimal::add)));
        return sellerRevenueMap.entrySet().stream()
                .map(entry -> {
                    User seller = entry.getKey();
                    BigDecimal sellerRevenue = entry.getValue();
                    TopSellerDto dto = new TopSellerDto();
                    dto.setSellerId(seller.getId());
                    dto.setSellerName(seller.getName());
                    dto.setTotalRevenue(sellerRevenue);
                    return dto;
                })
                .sorted((s1, s2) -> s2.getTotalRevenue().compareTo(s1.getTotalRevenue()))
                .limit(10)
                .toList();
    }
}
