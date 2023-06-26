package com.truthyouth.commerce.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.truthyouth.commerce.dto.response.ResponseDto;
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(GlobalException.class)
    public ResponseEntity<?> handleUserSignupException(GlobalException ex) {
        ResponseDto responseDto = new ResponseDto();
        responseDto.setMessage(ex.getMessage());
        responseDto.setStatus("failed");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDto);
    }
}