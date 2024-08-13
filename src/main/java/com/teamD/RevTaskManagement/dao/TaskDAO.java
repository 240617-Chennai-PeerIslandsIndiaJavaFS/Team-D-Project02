package com.teamD.RevTaskManagement.dao;

import com.teamD.RevTaskManagement.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskDAO extends JpaRepository<Task, Long> {
    // Custom query methods can be defined here if needed
}
