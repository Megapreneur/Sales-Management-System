package com.uncledemy.salesmanagementsystem.dto;

import com.uncledemy.salesmanagementsystem.model.Sales;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SalesReportDto {
    private int totalSales;
    private BigDecimal totalRevenue;
    private List<TopProductDto> topSellingProducts;
    private List<TopSellerDto> topPerformingSellers;


}
