package com.teamD.RevTaskManagement.dao;

import com.teamD.RevTaskManagement.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectDAO extends JpaRepository<Project,Long> {
    Project findByProjectName(String name);
}
