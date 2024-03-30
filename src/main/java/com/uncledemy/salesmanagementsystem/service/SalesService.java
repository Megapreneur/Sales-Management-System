package com.uncledemy.salesmanagementsystem.service;

import com.uncledemy.salesmanagementsystem.dto.*;
import com.uncledemy.salesmanagementsystem.exception.SalesManagementException;
import com.uncledemy.salesmanagementsystem.model.Product;
import com.uncledemy.salesmanagementsystem.model.Sales;

import java.util.List;

public interface SalesService {
    boolean createSales(Long userId,SalesDto salesDto) throws SalesManagementException;
    boolean updateSales(long SalesId, long userId, SalesUpdateDto salesUpdateDto) throws SalesManagementException;
    Sales findSalesById(long salesId) throws SalesManagementException;
    List<Sales> getAllSales();
    SalesReportDto generateSalesReport(String startDate, String endDate);
}
