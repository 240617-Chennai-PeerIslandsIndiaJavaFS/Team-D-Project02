package com.teamD.RevTaskManagement.service;


import com.teamD.RevTaskManagement.dao.ProjectDAO;
import com.teamD.RevTaskManagement.exceptions.NotFoundException;
import com.teamD.RevTaskManagement.exceptions.ProjectNotFoundException;
import com.teamD.RevTaskManagement.model.Project;
import com.teamD.RevTaskManagement.utilities.ModelUpdater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {

    @Autowired
    private ProjectDAO projectDAO;

    @Autowired
    private ModelUpdater modelUpdater;

    public Project createProject(Project project){
        return projectDAO.save(project);
    }

    public Project updateProject(Long id,Project projectDetails){
        Optional<Project> existing_project = projectDAO.findById(id);

        if(existing_project.isPresent()){
            Project existingProject = existing_project.get();

            modelUpdater.updateFields(existingProject, projectDetails);

            return projectDAO.save(existingProject);
        }

        else {
            throw new ProjectNotFoundException("Project not found with id " + id);
        }

    }

    public Project getProjectById(Long id){
        return projectDAO.findById(id).orElseThrow(()->
                new ProjectNotFoundException("Project not found with id " + id)
        );
    }

    public List<Project> getAllProjects(){
        return projectDAO.findAll();
    }

    public Project deleteProjectById(Long id){

        if(projectDAO.existsById(id)){
            Project project = projectDAO.findById(id).get();
            projectDAO.deleteById(id);
            return project;
        }
        else{
            throw new ProjectNotFoundException("Project not found with id " + id);
        }
    }

    public Project fetchProjectByName(String name){
        Project dbProject=projectDAO.findByProjectName(name);
        if(dbProject==null){
            throw new NotFoundException("Project with name: "+name+" not found");
        }
        return dbProject;
    }
}
