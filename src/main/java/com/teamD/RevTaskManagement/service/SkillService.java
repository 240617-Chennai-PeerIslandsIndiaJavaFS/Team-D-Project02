package com.teamD.RevTaskManagement.service;

import com.teamD.RevTaskManagement.dao.SkillDAO;
import com.teamD.RevTaskManagement.exceptions.NotFoundException;
import com.teamD.RevTaskManagement.model.Skill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class SkillService {

    @Autowired
    SkillDAO skillDAO;

    public Skill createSkill(Skill skill){
        return skillDAO.save(skill);
    }

    public Skill getSkillByName(String skill){
        Skill dbSkill=skillDAO.findBySkill(skill);
        if(dbSkill==null){
            throw new NotFoundException("No skill with name: "+skill);
        }
        return dbSkill;
    }
}