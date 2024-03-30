package com.uncledemy.salesmanagementsystem.controller;

import com.uncledemy.salesmanagementsystem.constants.StatusConstant;
import com.uncledemy.salesmanagementsystem.dto.*;
import com.uncledemy.salesmanagementsystem.exception.InvalidPhoneNumberException;
import com.uncledemy.salesmanagementsystem.exception.SalesManagementException;
import com.uncledemy.salesmanagementsystem.model.Client;
import com.uncledemy.salesmanagementsystem.model.Sales;
import com.uncledemy.salesmanagementsystem.service.SalesService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/sales")
public class SalesController {
    private SalesService salesService;
    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createSales(@Valid @RequestParam Long userId,@Valid @RequestBody SalesDto salesDto) throws SalesManagementException {
        boolean isCreated = salesService.createSales(userId,salesDto);
        if (isCreated){
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(StatusConstant.STATUS_200, StatusConstant.MESSAGE_200));
        }else {
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(StatusConstant.STATUS_417, StatusConstant.MESSAGE_417_UPDATE));
        }
    }
    @GetMapping("/sales")
    public ResponseEntity<Sales> findATransaction(@Valid @RequestParam long salesId) throws SalesManagementException {
        Sales sales = salesService.findSalesById(salesId);
        return ResponseEntity.status(HttpStatus.OK).body(sales);
    }
    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateSales(@Valid @RequestParam long salesId,
                                                            @Valid @RequestParam long userId,
                                                            @Valid @RequestBody SalesUpdateDto salesUpdateDto) throws SalesManagementException, InvalidPhoneNumberException {
        boolean isUpdated = salesService.updateSales(salesId,userId,salesUpdateDto);
        if (isUpdated){
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(StatusConstant.STATUS_200, StatusConstant.MESSAGE_200));
        }else {
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(StatusConstant.STATUS_417, StatusConstant.MESSAGE_417_UPDATE));
        }
    }
    @GetMapping("/allTransactions")
    public ResponseEntity<List<Sales>> getAllTransactions(){
        List<Sales> sales = salesService.getAllSales();
        return ResponseEntity.status(HttpStatus.OK).body(sales);
    }
    @GetMapping("/reports/sales")
    public ResponseEntity<SalesReportDto> getSalesReport(@Valid @RequestParam String startDate,
                                                          @Valid @RequestParam String endDate) {
        SalesReportDto salesReport = salesService.generateSalesReport(startDate, endDate);
        return ResponseEntity.status(HttpStatus.OK).body(salesReport);
    }
}
