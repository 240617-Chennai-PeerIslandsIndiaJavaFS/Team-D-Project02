package com.teamD.RevTaskManagement.dao;

import com.teamD.RevTaskManagement.model.Timeline;
import com.teamD.RevTaskManagement.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TimelineDAO extends JpaRepository<Timeline, Long> {
    List<Timeline> findByTask(Task task);
}
