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
        BaseResponse<String> baseResponse=new BaseResponse<>("Error",ex.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(baseResponse,HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<BaseResponse<String>> credentialsExceptionHandler(InvalidCredentialsException ex){
        BaseResponse<String> baseResponse=new BaseResponse<>("Error",ex.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(baseResponse,HttpStatus.BAD_REQUEST);
    }

}
