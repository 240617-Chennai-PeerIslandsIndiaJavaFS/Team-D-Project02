package com.teamD.RevTaskManagement.controller;


import com.teamD.RevTaskManagement.model.Project;
import com.teamD.RevTaskManagement.service.ProjectService;
import com.teamD.RevTaskManagement.utilities.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<Project>> getProjectById(@PathVariable Long id){
        BaseResponse<Project> baseResponse=new BaseResponse<>("Project Fetched successfully",projectService.getProjectById(id),HttpStatus.ACCEPTED.value());
        return new ResponseEntity<>(baseResponse,HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<BaseResponse<List<Project>>> getAllProjects(){
        BaseResponse<List<Project>> baseResponse = new BaseResponse<>("All Projects are Fetched successfully",projectService.getAllProjects(),HttpStatus.ACCEPTED.value());
        return new ResponseEntity<>(baseResponse,HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<BaseResponse<Project>> createProject(@RequestBody Project project){
        BaseResponse<Project> baseResponse = new BaseResponse<>("Project is Created Successfully",projectService.createProject(project),HttpStatus.CREATED.value());
        return new ResponseEntity<>(baseResponse,HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse<Project>> updateProject(@PathVariable Long id,@RequestBody Project projectDetails){
        BaseResponse<Project> baseResponse = new BaseResponse<>("Project Updated Successfully",projectService.updateProject(id,projectDetails),HttpStatus.OK.value());
        return new ResponseEntity<>(baseResponse,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<Project>> deleteProject(@PathVariable Long id){
        BaseResponse<Project> baseResponse = new BaseResponse<>("Project is deleted Successfully",projectService.deleteProjectById(id),HttpStatus.OK.value());
        return new ResponseEntity<>(baseResponse,HttpStatus.OK);
    }

    @GetMapping("/name")
    public ResponseEntity<BaseResponse<Project>> fetchByName(@RequestParam String name){
        BaseResponse<Project> baseResponse = new BaseResponse<>("Project Fetched ",projectService.fetchProjectByName(name),HttpStatus.OK.value());
        return new ResponseEntity<>(baseResponse,HttpStatus.OK);
    }

    @GetMapping("/employee/{id}")
    public ResponseEntity<BaseResponse<List<Project>>> fetchUserProjects(@PathVariable long id){
        BaseResponse<List<Project>> baseResponse=new BaseResponse<>("Fetched projects",projectService.fetchUserProjects(id),HttpStatus.OK.value());
        return new ResponseEntity<>(baseResponse,HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<BaseResponse<Project>> addUserIntoProject(@RequestParam long project_id,@RequestParam long employee_id){
        BaseResponse<Project> baseResponse=new BaseResponse<>("Added successfully",projectService.addUserIntoProject(project_id,employee_id),HttpStatus.OK.value());
        return new ResponseEntity<>(baseResponse,HttpStatus.OK);
    }

    @DeleteMapping("/team")
    public ResponseEntity<BaseResponse<Project>> removeUserFromProject(@RequestParam long project_id,@RequestParam long employee_id){
        BaseResponse<Project> baseResponse=new BaseResponse<>("Deleted user",projectService.removeUserFromProject(project_id,employee_id),HttpStatus.OK.value());
        return new ResponseEntity<>(baseResponse,HttpStatus.OK);
    }




}
