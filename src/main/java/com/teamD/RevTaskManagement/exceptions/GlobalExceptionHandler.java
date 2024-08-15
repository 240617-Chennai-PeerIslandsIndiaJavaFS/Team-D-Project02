package com.teamD.RevTaskManagement.exceptions;

import com.teamD.RevTaskManagement.utilities.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ProjectNotFoundException.class)
    public ResponseEntity<BaseResponse<String>> handleProjectNotFoundException(ProjectNotFoundException ex) {
        BaseResponse<String> response = new BaseResponse<>(ex.getMessage(), null, HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ClientNotFoundException.class)
    public ResponseEntity<BaseResponse<String>> handleClientNotFoundException(ClientNotFoundException ex) {
        BaseResponse<String> response = new BaseResponse<>(ex.getMessage(), null, HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
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


    @ExceptionHandler(ImageNotFoundException.class)
    public ResponseEntity<BaseResponse<String>> handleImageNotFoundException(ImageNotFoundException ex) {
        BaseResponse<String> response = new BaseResponse<>("error", ex.getMessage(), HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MessageNotFoundException.class)
    public ResponseEntity<BaseResponse<String>> handleMessageNotFoundException(MessageNotFoundException ex) {
        BaseResponse<String> response = new BaseResponse<>(ex.getMessage(), null, HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<BaseResponse<String>> handleImageNotFoundException(NotFoundException ex) {
        BaseResponse<String> response = new BaseResponse<>("error", ex.getMessage(), HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
}

    @ExceptionHandler(CommentNotFoundException.class)
    public ResponseEntity<BaseResponse<String>> handleCommentNotFoundException(CommentNotFoundException ex){
        BaseResponse<String> response = new BaseResponse<>(ex.getMessage(),null,HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
    }

    
}
