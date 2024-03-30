package com.uncledemy.salesmanagementsystem.controller;

import com.uncledemy.salesmanagementsystem.constants.StatusConstant;
import com.uncledemy.salesmanagementsystem.dto.ClientDto;
import com.uncledemy.salesmanagementsystem.dto.ProductDto;
import com.uncledemy.salesmanagementsystem.dto.ProductReportDto;
import com.uncledemy.salesmanagementsystem.dto.ResponseDto;
import com.uncledemy.salesmanagementsystem.exception.InvalidPhoneNumberException;
import com.uncledemy.salesmanagementsystem.exception.SalesManagementException;
import com.uncledemy.salesmanagementsystem.model.Client;
import com.uncledemy.salesmanagementsystem.model.Product;
import com.uncledemy.salesmanagementsystem.service.ProductService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/product")
public class ProductController {
    private ProductService productService;


    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createProduct(@Valid @RequestBody ProductDto productDto){
        productService.createProduct(productDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(StatusConstant.STATUS_201, StatusConstant.MESSAGE_201));
    }
    @GetMapping("/product")
    public ResponseEntity<Product> getAProduct(@RequestParam long productId) throws SalesManagementException {
        Product product = productService.findProductById(productId);
        return ResponseEntity.status(HttpStatus.OK).body(product);
    }
    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateProduct(@Valid @RequestParam long productId, @Valid @RequestBody ProductDto productDto) throws SalesManagementException{
        boolean isUpdated = productService.updateProduct(productId, productDto);
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
    @GetMapping("/allProducts")
    public ResponseEntity<List<Product>> getAllProduct(){
        List<Product> products = productService.getAllProduct();
        return ResponseEntity.status(HttpStatus.OK).body(products);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteAProduct(@RequestParam long productId) throws SalesManagementException {
        boolean isDeleted = productService.deleteProduct(productId);
        if (isDeleted){
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(StatusConstant.STATUS_200, StatusConstant.MESSAGE_200));
        }else{
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(StatusConstant.STATUS_417, StatusConstant.MESSAGE_417_DELETE));
        }
    }
    @GetMapping("/reports/product")
    public ResponseEntity<ProductReportDto> getProductReport() {
        ProductReportDto productReport = productService.generateProductReport();
        return ResponseEntity.status(HttpStatus.OK).body(productReport);
    }
}
