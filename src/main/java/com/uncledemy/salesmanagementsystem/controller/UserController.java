package com.uncledemy.salesmanagementsystem.controller;

import com.uncledemy.salesmanagementsystem.constants.StatusConstant;
import com.uncledemy.salesmanagementsystem.dto.ResponseDto;
import com.uncledemy.salesmanagementsystem.dto.StaffDto;
import com.uncledemy.salesmanagementsystem.exception.InvalidPasswordException;
import com.uncledemy.salesmanagementsystem.exception.PasswordMisMatchException;
import com.uncledemy.salesmanagementsystem.exception.UserAlreadyExistException;
import com.uncledemy.salesmanagementsystem.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("api/v1/user")
public class UserController {
    private UserService userService;

    @PostMapping("/create")

    public ResponseEntity<ResponseDto> createAccount(@Valid @RequestBody StaffDto staffDto) throws UserAlreadyExistException, InvalidPasswordException, PasswordMisMatchException {
        userService.staffRegistration(staffDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(StatusConstant.STATUS_201, StatusConstant.MESSAGE_201));
    }
}
