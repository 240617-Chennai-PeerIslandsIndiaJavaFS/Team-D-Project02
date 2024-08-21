package com.teamD.RevTaskManagement.service;


import com.teamD.RevTaskManagement.dao.EmployeeDAO;
import com.teamD.RevTaskManagement.dao.ProjectDAO;
import com.teamD.RevTaskManagement.exceptions.InvalidCredentialsException;
import com.teamD.RevTaskManagement.exceptions.NotFoundException;
import com.teamD.RevTaskManagement.exceptions.ProjectNotFoundException;
import com.teamD.RevTaskManagement.model.Employee;
import com.teamD.RevTaskManagement.model.Project;
import com.teamD.RevTaskManagement.utilities.ModelUpdater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProjectService {

    @Autowired
    private ProjectDAO projectDAO;

    @Autowired
    private EmployeeDAO employeeDAO;

    @Autowired
    private ModelUpdater modelUpdater;

    public Project createProject(Project project){
        List<Employee> managedEmployees = project.getTeam().stream()
                .map(employee -> employeeDAO.findById(employee.getEmployeeId())
                        .orElseThrow(() -> new IllegalArgumentException("Employee not found: " + employee.getEmployeeId())))
                .collect(Collectors.toList());

        project.setTeam(managedEmployees);
        return projectDAO.save(project);
    }

    public Project updateProject(Long id,Project projectDetails){
        Optional<Project> existing_project = projectDAO.findById(id);

        if(existing_project.isPresent()){
            Project existingProject = existing_project.get();

            modelUpdater.updateFields(existingProject, projectDetails);

            List<Employee> existingEmployees = existingProject.getTeam();
            List<Employee> updatedEmployees = projectDetails.getTeam().stream()
                    .map(employee -> employeeDAO.findById(employee.getEmployeeId()))
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toList());

            Employee newManager = updatedEmployees.stream()
                    .filter(employee -> employee.getEmployeeId()==(projectDetails.getTeam().get(0).getEmployeeId()))
                    .findFirst()
                    .orElse(null);

            if (newManager != null) {
                existingEmployees.removeIf(emp -> emp.getEmployeeId()==(newManager.getEmployeeId()));
                existingEmployees.add(newManager);
            }

            existingProject.setTeam(existingEmployees);

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

    public List<Project> fetchUserProjects(Long user_id){
        return projectDAO.findAll().stream()
                .filter(project -> project.getTeam().stream()
                        .anyMatch(employee -> employee.getEmployeeId()==(user_id)))
                .toList();
    }

    public Project addUserIntoProject(long project_id,long employee_id){
        Employee employee=employeeDAO.findById(employee_id).get();
        Project project=getProjectById(project_id);
        List<Employee> teams=project.getTeam();
        teams.add(employee);
        project.setTeam(teams);
        Project dbProject=projectDAO.save(project);
        return dbProject;
    }

    public Project removeUserFromProject(long project_id, long employee_id) {
    Project project = getProjectById(project_id);
    List<Employee> employees = project.getTeam();
    project.setTeam(employees.stream()
            .filter(employee -> employee.getEmployeeId() != employee_id)
            .collect(Collectors.toList())); // Add collect(Collectors.toList()) here
    return projectDAO.save(project);
}
}
