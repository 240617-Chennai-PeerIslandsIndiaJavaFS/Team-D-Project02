package com.teamD.RevTaskManagement.dao;

import com.teamD.RevTaskManagement.model.Comment;
import com.teamD.RevTaskManagement.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentDAO extends JpaRepository<Comment,Long> {
    List<Comment> findByTask(Task task);
}
