package com.teamD.RevTaskManagement.exceptions;

import com.teamD.RevTaskManagement.utilities.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ProjectNotFoundException.class)
    public ResponseEntity<BaseResponse<String>> handleUserNotFoundException(ProjectNotFoundException ex){
        BaseResponse<String> response = new BaseResponse<>(ex.getMessage(),null,HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<BaseResponse<String>> handleTaskNotFoundException(TaskNotFoundException ex) {
        BaseResponse<String> response = new BaseResponse<>(
                ex.getMessage(),
                null,
                HttpStatus.NOT_FOUND.value()
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TimelineNotFoundException.class)
    public ResponseEntity<BaseResponse<String>> handleTimelineNotFoundException(TimelineNotFoundException ex) {
        BaseResponse<String> response = new BaseResponse<>(ex.getMessage(), null, HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseResponse<String>> handleGeneralException(Exception ex) {
        BaseResponse<String> response = new BaseResponse<>(
                "An unexpected error occurred: " + ex.getMessage(),
                null,
                HttpStatus.INTERNAL_SERVER_ERROR.value()
        );
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
