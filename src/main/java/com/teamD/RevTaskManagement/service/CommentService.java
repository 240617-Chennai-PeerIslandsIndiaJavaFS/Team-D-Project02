package com.teamD.RevTaskManagement.service;

import com.teamD.RevTaskManagement.dao.CommentDAO;
import com.teamD.RevTaskManagement.dao.TaskDAO;
import com.teamD.RevTaskManagement.exceptions.CommentNotFoundException;
import com.teamD.RevTaskManagement.exceptions.TaskNotFoundException;
import com.teamD.RevTaskManagement.model.Comment;
import com.teamD.RevTaskManagement.model.Task;
import com.teamD.RevTaskManagement.utilities.ModelUpdater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {
    @Autowired
    private CommentDAO commentDAO;

    @Autowired
    private TaskDAO taskDAO;

    @Autowired
    private ModelUpdater modelUpdater;

    public Comment createComment(Comment comment) {
        return commentDAO.save(comment);
    }

    public Comment updateComment(Long id, Comment commentDetails) {
        Comment existingComment = commentDAO.findById(id)
                .orElseThrow(() -> new CommentNotFoundException("Comment not found with id: " + id));
        Comment existing = modelUpdater.updateFields(existingComment, commentDetails);
        return commentDAO.save(existing);
    }

    public Comment getCommentById(Long id) {
        return commentDAO.findById(id).orElseThrow(() -> new CommentNotFoundException("Comment not found with id: " + id));
    }

    public List<Comment> getAllCommentsByTaskId(Long taskId) {
        Task task = taskDAO.findById(taskId).orElseThrow(() -> new TaskNotFoundException("Task not found with id: " + taskId));;
        return commentDAO.findByTask(task);
    }

    public void deleteCommentById(Long id) {
        Comment comment = commentDAO.findById(id)
                .orElseThrow(() -> new CommentNotFoundException("Comment not found with id: " + id));
        commentDAO.delete(comment);
    }
}
