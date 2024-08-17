package com.teamD.RevTaskManagement.controller;

import com.teamD.RevTaskManagement.model.Comment;
import com.teamD.RevTaskManagement.model.Task;
import com.teamD.RevTaskManagement.service.CommentService;
import com.teamD.RevTaskManagement.utilities.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping("/task/{taskId}")
    public ResponseEntity<BaseResponse<List<Comment>>> getAllCommentsByTaskId(@PathVariable Long taskId) {
        BaseResponse<List<Comment>> baseResponse = new BaseResponse<>("Comments Fetched Successfully", commentService.getAllCommentsByTaskId(taskId), HttpStatus.OK.value());
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<BaseResponse<Comment>> createComment(@RequestBody Comment comment) {
        BaseResponse<Comment> baseResponse = new BaseResponse<>("Comment Created Successfully", commentService.createComment(comment), HttpStatus.CREATED.value());
        return new ResponseEntity<>(baseResponse, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse<Comment>> updateComment(@PathVariable Long id, @RequestBody Comment commentDetails) {
        BaseResponse<Comment> baseResponse = new BaseResponse<>("Comment Updated Successfully", commentService.updateComment(id, commentDetails), HttpStatus.OK.value());
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<Comment>> deleteCommentById(@PathVariable Long id) {
        Comment comment = commentService.getCommentById(id);
        commentService.deleteCommentById(id);
        BaseResponse<Comment> baseResponse = new BaseResponse<>("Comment Deleted Successfully", comment, HttpStatus.OK.value());
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @GetMapping("/task/{taskId}/comments")
    public ResponseEntity<BaseResponse<List<Comment>>> getCommentsByTaskId(@PathVariable Long taskId) {
        BaseResponse<List<Comment>> baseResponse = new BaseResponse<>("Comments Fetched Successfully", commentService.getAllCommentsByTaskId(taskId), HttpStatus.OK.value());
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @PostMapping("/task/comments")
    public ResponseEntity<BaseResponse<List<Comment>>> getCommentsByTask(@RequestBody Task task) {
        BaseResponse<List<Comment>> baseResponse = new BaseResponse<>("Comments Fetched Successfully", commentService.getAllCommentsByTask(task), HttpStatus.OK.value());
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }
}
