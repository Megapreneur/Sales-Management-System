package com.uncledemy.salesmanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ProductSalesDto {
    private Long productId;
    private String productName;
    private int totalUnitsSold;
    private BigDecimal totalRevenue;
}
