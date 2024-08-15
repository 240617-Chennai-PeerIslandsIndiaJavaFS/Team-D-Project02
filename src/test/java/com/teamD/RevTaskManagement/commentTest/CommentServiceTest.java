package com.teamD.RevTaskManagement.commentTest;

import com.teamD.RevTaskManagement.dao.CommentDAO;
import com.teamD.RevTaskManagement.dao.TaskDAO;
import com.teamD.RevTaskManagement.exceptions.CommentNotFoundException;
import com.teamD.RevTaskManagement.exceptions.TaskNotFoundException;
import com.teamD.RevTaskManagement.model.Comment;
import com.teamD.RevTaskManagement.model.Task;
import com.teamD.RevTaskManagement.service.CommentService;
import com.teamD.RevTaskManagement.utilities.ModelUpdater;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CommentServiceTest {

    @Mock
    private CommentDAO commentDAO;

    @Mock
    private TaskDAO taskDAO;

    @Mock
    private ModelUpdater modelUpdater;

    @InjectMocks
    private CommentService commentService;

    private Comment comment1;
    private Comment comment2;
    private Task task;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

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
    void testCreateComment() {
        when(commentDAO.save(any(Comment.class))).thenReturn(comment1);

        Comment savedComment = commentService.createComment(comment1);

        assertNotNull(savedComment);
        assertEquals(comment1.getComment(), savedComment.getComment());
    }

    @Test
    void testUpdateComment() {
        when(commentDAO.findById(anyLong())).thenReturn(Optional.of(comment1));
        when(modelUpdater.updateFields(any(Comment.class), any(Comment.class))).thenReturn(comment1);
        when(commentDAO.save(any(Comment.class))).thenReturn(comment1);

        Comment updatedComment = commentService.updateComment(1L, comment1);

        assertNotNull(updatedComment);
        assertEquals(comment1.getComment(), updatedComment.getComment());
    }

    @Test
    void testGetCommentById() {
        when(commentDAO.findById(anyLong())).thenReturn(Optional.of(comment1));

        Comment foundComment = commentService.getCommentById(1L);

        assertNotNull(foundComment);
        assertEquals(comment1.getComment(), foundComment.getComment());
    }

    @Test
    void testGetAllCommentsByTaskId() {
        when(taskDAO.findById(anyLong())).thenReturn(Optional.of(task));
        when(commentDAO.findByTask(any(Task.class))).thenReturn(Arrays.asList(comment1, comment2));

        List<Comment> comments = commentService.getAllCommentsByTaskId(1L);

        assertNotNull(comments);
        assertEquals(2, comments.size());
    }

    @Test
    void testDeleteCommentById() {
        when(commentDAO.findById(anyLong())).thenReturn(Optional.of(comment1));
        doNothing().when(commentDAO).delete(any(Comment.class));

        assertDoesNotThrow(() -> commentService.deleteCommentById(1L));
    }
}