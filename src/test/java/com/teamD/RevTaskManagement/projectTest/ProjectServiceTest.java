package com.teamD.RevTaskManagement.projectTest;


import com.teamD.RevTaskManagement.dao.ProjectDAO;
import com.teamD.RevTaskManagement.model.Project;
import com.teamD.RevTaskManagement.service.ProjectService;
import com.teamD.RevTaskManagement.utilities.ModelUpdater;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.text.ParseException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import java.text.SimpleDateFormat;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProjectServiceTest {
    @Mock
    private ProjectDAO projectDAO;

    @Mock
    private ModelUpdater modelUpdater;

    @InjectMocks
    private ProjectService projectService;

    private Project project1;
    private Project project2;
    private List<Project> projectList;

    public ProjectServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @BeforeEach
    void setUp() throws ParseException {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        project1 = new Project();
        project1.setProjectId(1L);
        project1.setProjectName("Project One");
        project1.setDescription("Description One");
        project1.setStartDate(dateFormat.parse("2024-01-01"));
        project1.setEndDate(dateFormat.parse("2024-12-31"));

        project2 = new Project();
        project2.setProjectId(2L);
        project2.setProjectName("Project Two");
        project2.setDescription("Description Two");
        project2.setStartDate(dateFormat.parse("2024-01-01"));
        project2.setEndDate(dateFormat.parse("2024-12-31"));

        projectList = Arrays.asList(project1, project2);
    }

    @Test
    void testCreateProject() {
        when(projectDAO.save(project1)).thenReturn(project1);

        Project createdProject = projectService.createProject(project1);
        assertNotNull(createdProject);
        assertEquals("Project One", createdProject.getProjectName());
    }

    @Test
    public void testUpdateProject() {
        when(projectDAO.findById(1L)).thenReturn(Optional.ofNullable(project1));
        when(projectDAO.save(project1)).thenReturn(project1);

        Project updatedProject = projectService.updateProject(1L, project2);

        verify(modelUpdater, times(1)).updateFields(project1, project2);
        verify(projectDAO, times(1)).save(project1);

        assertEquals(project1, updatedProject);
    }

    @Test
    void testGetProjectById() {
        when(projectDAO.findById(1L)).thenReturn(Optional.of(project1));

        Project foundProject = projectService.getProjectById(1L);
        assertNotNull(foundProject);
        assertEquals("Project One", foundProject.getProjectName());
    }

    @Test
    void testGetAllProjects() {
        when(projectDAO.findAll()).thenReturn(projectList);

        List<Project> allProjects = projectService.getAllProjects();
        assertNotNull(allProjects);
        assertEquals(2, allProjects.size());
    }

    @Test
    void testDeleteProjectById() {
        when(projectDAO.existsById(1L)).thenReturn(true);
        when(projectDAO.findById(1L)).thenReturn(Optional.of(project1));

        Project deletedProject = projectService.deleteProjectById(1L);

        assertNotNull(deletedProject);
        assertEquals(1L, deletedProject.getProjectId());

        verify(projectDAO, times(1)).deleteById(1L);
    }
}
