package com.teamD.RevTaskManagement.dao;

import com.teamD.RevTaskManagement.model.Skill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SkillDAO extends JpaRepository<Skill,Long> {
    Skill findBySkill(String skill);
}
