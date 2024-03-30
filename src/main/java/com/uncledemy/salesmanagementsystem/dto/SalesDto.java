package com.uncledemy.salesmanagementsystem.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.uncledemy.salesmanagementsystem.model.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SalesDto {
    private String phoneNumber;
    private Map<Long, Integer> productQuantities = new HashMap<>();
    public int getTotalQuantity() {
        // Calculate and return the total quantity of products in the sales
        int totalQuantity = 0;
        for (int quantity : productQuantities.values()) {
            totalQuantity += quantity;
        }
        return totalQuantity;
    }



}
