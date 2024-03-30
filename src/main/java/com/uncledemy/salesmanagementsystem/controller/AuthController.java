package com.uncledemy.salesmanagementsystem.controller;

import com.uncledemy.salesmanagementsystem.dto.AuthenticationResponse;
import com.uncledemy.salesmanagementsystem.dto.LoginDto;
import com.uncledemy.salesmanagementsystem.exception.SalesManagementException;
import com.uncledemy.salesmanagementsystem.exception.UserNotFoundException;
import com.uncledemy.salesmanagementsystem.service.AuthService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private AuthService authService;
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody @Valid LoginDto loginDto) throws UserNotFoundException, SalesManagementException {
        return ResponseEntity.ok(authService.login(loginDto));
    }
}
