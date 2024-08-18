package com.teamD.RevTaskManagement.service;

import com.teamD.RevTaskManagement.dao.SkillDAO;
import com.teamD.RevTaskManagement.exceptions.NotFoundException;
import com.teamD.RevTaskManagement.model.Skill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SkillService {

    @Autowired
    SkillDAO skillDAO;

    public Skill createSkill(Skill skill){
        Skill existingSkill = skillDAO.findBySkill(skill.getSkill());
        if (existingSkill != null) {
            return existingSkill; // Return existing skill
        }
        return skillDAO.save(skill);
    }

    public Skill getSkillByName(String skill){
        Skill dbSkill=skillDAO.findBySkill(skill);
        if(dbSkill==null){
            throw new NotFoundException("No skill with name: "+skill);
        }
        return dbSkill;
    }

    public List<Skill> getAllSkills(){
        return skillDAO.findAll();
    }
}
