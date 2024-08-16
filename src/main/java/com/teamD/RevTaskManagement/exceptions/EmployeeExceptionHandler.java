package com.teamD.RevTaskManagement.exceptions;

import com.teamD.RevTaskManagement.utilities.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class EmployeeExceptionHandler {
    @ExceptionHandler(InvalidEmailException.class)
    public ResponseEntity<BaseResponse<String>> mailExceptionHandler(InvalidEmailException ex){
        BaseResponse<String> baseResponse=new BaseResponse<>("Error",ex.getMessage(), HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(baseResponse,HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<BaseResponse<String>> credentialsExceptionHandler(InvalidCredentialsException ex){
        BaseResponse<String> baseResponse=new BaseResponse<>("Error",ex.getMessage(), HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(baseResponse,HttpStatus.NOT_FOUND);
    }

}
