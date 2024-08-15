package com.teamD.RevTaskManagement.commentTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.teamD.RevTaskManagement.controller.CommentController;
import com.teamD.RevTaskManagement.model.Comment;
import com.teamD.RevTaskManagement.model.Task;
import com.teamD.RevTaskManagement.service.CommentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CommentController.class)
public class CommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CommentService commentService;

    @Autowired
    private ObjectMapper objectMapper;

    private Comment comment1;
    private Comment comment2;
    private Task task;

    @BeforeEach
    void setUp() {
        task = new Task();
        task.setTaskId(1L);
        task.setTaskName("Task One");

        comment1 = new Comment();
        comment1.setCommentId(1L);
        comment1.setComment("This is the first comment.");
        comment1.setTask(task);

        comment2 = new Comment();
        comment2.setCommentId(2L);
        comment2.setComment("This is the second comment.");
        comment2.setTask(task);
    }

    @Test
    void testGetAllCommentsByTaskId() throws Exception {
        List<Comment> commentList = Arrays.asList(comment1, comment2);
        when(commentService.getAllCommentsByTaskId(anyLong())).thenReturn(commentList);

        mockMvc.perform(get("/api/comments/task/{taskId}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.length()").value(2))
                .andExpect(jsonPath("$.status").value(HttpStatus.OK.value()));
    }

    @Test
    void testCreateComment() throws Exception {
        when(commentService.createComment(any(Comment.class))).thenReturn(comment1);

        mockMvc.perform(post("/api/comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(comment1)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.comment").value("This is the first comment."))
                .andExpect(jsonPath("$.status").value(HttpStatus.CREATED.value()));
    }

    @Test
    void testUpdateComment() throws Exception {
        when(commentService.updateComment(anyLong(), any(Comment.class))).thenReturn(comment1);

        mockMvc.perform(put("/api/comments/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(comment1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.comment").value("This is the first comment."))
                .andExpect(jsonPath("$.status").value(HttpStatus.OK.value()));
    }

    @Test
    void testDeleteComment() throws Exception {
        when(commentService.getCommentById(anyLong())).thenReturn(comment1);

        mockMvc.perform(delete("/api/comments/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.comment").value("This is the first comment."))
                .andExpect(jsonPath("$.status").value(HttpStatus.OK.value()));
    }
}
