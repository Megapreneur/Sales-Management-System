package com.uncledemy.salesmanagementsystem.exception;


import com.uncledemy.salesmanagementsystem.util.APIError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<APIError> handleException(InvalidPasswordException e){
        e.printStackTrace();
        return ResponseEntity.badRequest().body(APIError.builder()
                .status(HttpStatus.BAD_REQUEST)
                .message(e.getLocalizedMessage())
                .build());
    }

    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<APIError> handleException(UserAlreadyExistException e){
        e.printStackTrace();
        return ResponseEntity.badRequest().body(APIError.builder()
                .status(HttpStatus.BAD_REQUEST)
                .message(e.getLocalizedMessage())
                .build());
    }

    @ExceptionHandler(PasswordMisMatchException.class)
    public ResponseEntity<APIError> handleException(PasswordMisMatchException e){
        e.printStackTrace();
        return ResponseEntity.badRequest().body(APIError.builder()
                .status(HttpStatus.BAD_REQUEST)
                .message(e.getLocalizedMessage())
                .build());
    }
    @ExceptionHandler(InvalidPhoneNumberException.class)
    public ResponseEntity<APIError> handleException(InvalidPhoneNumberException e){
        e.printStackTrace();
        return ResponseEntity.badRequest().body(APIError.builder()
                .status(HttpStatus.BAD_REQUEST)
                .message(e.getLocalizedMessage())
                .build());
    }


    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<APIError> handleException(UserNotFoundException e){
        e.printStackTrace();
        return ResponseEntity.badRequest().body(APIError.builder()
                .status(HttpStatus.BAD_REQUEST)
                .message(e.getLocalizedMessage())
                .build());
    }
    @ExceptionHandler(SalesManagementException.class)
    public ResponseEntity<APIError> handleException(SalesManagementException e){
        e.printStackTrace();
        return ResponseEntity.badRequest().body(APIError.builder()
                .status(HttpStatus.BAD_REQUEST)
                .message(e.getLocalizedMessage())
                .build());
    }

}
