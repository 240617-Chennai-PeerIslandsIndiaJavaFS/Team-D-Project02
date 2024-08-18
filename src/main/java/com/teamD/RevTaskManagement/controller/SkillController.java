package com.teamD.RevTaskManagement.controller;

import com.teamD.RevTaskManagement.model.Skill;
import com.teamD.RevTaskManagement.service.SkillService;
import com.teamD.RevTaskManagement.utilities.BaseResponse;
import jakarta.persistence.AssociationOverride;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/skill")
public class SkillController {
    @Autowired
    SkillService skillService;

    @PostMapping
    public ResponseEntity<BaseResponse<Skill>> setSkill(@RequestBody Skill skill){
        BaseResponse<Skill> baseResponse=new BaseResponse<>("Skill added",skillService.createSkill(skill), HttpStatus.CREATED.value());
        return new ResponseEntity<>(baseResponse,HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<BaseResponse<Skill>> fetchBySkill(@RequestParam String skill){
        BaseResponse<Skill> baseResponse=new BaseResponse<>("fetched skill",skillService.getSkillByName(skill), HttpStatus.FOUND.value());
        return new ResponseEntity<>(baseResponse,HttpStatus.FOUND);
    }

    @GetMapping("/allSkills")
    public ResponseEntity<BaseResponse<List<Skill>>> getAllSkills(){
        BaseResponse<List<Skill>> baseResponse=new BaseResponse<>("All skills fetched",skillService.getAllSkills(), HttpStatus.OK.value());
        return new ResponseEntity<>(baseResponse,HttpStatus.OK);
    }
}
