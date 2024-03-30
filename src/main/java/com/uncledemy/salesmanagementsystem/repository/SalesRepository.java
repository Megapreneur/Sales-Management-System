package com.uncledemy.salesmanagementsystem.repository;

import com.uncledemy.salesmanagementsystem.model.Sales;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalesRepository extends JpaRepository<Sales,Long> {

}
