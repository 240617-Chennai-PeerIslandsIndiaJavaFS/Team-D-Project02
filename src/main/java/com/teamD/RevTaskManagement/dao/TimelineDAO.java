package com.teamD.RevTaskManagement.dao;

import com.teamD.RevTaskManagement.model.Timeline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TimelineDAO extends JpaRepository<Timeline, Long> {
    // Custom query methods can be added here if needed
}
