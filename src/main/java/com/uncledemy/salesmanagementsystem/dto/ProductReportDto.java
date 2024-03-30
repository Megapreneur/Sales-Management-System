package com.uncledemy.salesmanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductReportDto {
    private List<ProductInventoryDto> inventoryStatus;
    private List<ProductSalesDto> salesPerformance;
    private PricingAnalysisDto pricingAnalysis;
}
