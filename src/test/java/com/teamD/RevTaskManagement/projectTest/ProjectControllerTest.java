package com.teamD.RevTaskManagement.projectTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.teamD.RevTaskManagement.controller.ProjectController;
import com.teamD.RevTaskManagement.model.Project;
import com.teamD.RevTaskManagement.service.ProjectService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProjectController.class)
public class ProjectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProjectService projectService;

    @Autowired
    private ObjectMapper objectMapper;

    private Project project1;
    private Project project2;

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
    }

    @Test
    void testGetProjectById() throws Exception {
        when(projectService.getProjectById(anyLong())).thenReturn(project1);

        mockMvc.perform(get("/api/projects/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.projectName").value("Project One"))
                .andExpect(jsonPath("$.status").value(HttpStatus.ACCEPTED.value()));
    }

    @Test
    void testGetAllProjects() throws Exception {
        List<Project> projectList = Arrays.asList(project1, project2);
        when(projectService.getAllProjects()).thenReturn(projectList);

        mockMvc.perform(get("/api/projects"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.length()").value(2))
                .andExpect(jsonPath("$.status").value(HttpStatus.ACCEPTED.value()));
    }

    @Test
    void testCreateProject() throws Exception {
        when(projectService.createProject(any(Project.class))).thenReturn(project1);

        mockMvc.perform(post("/api/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(project1)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.projectName").value("Project One"))
                .andExpect(jsonPath("$.status").value(HttpStatus.CREATED.value()));
    }

    @Test
    void testUpdateProject() throws Exception {
        when(projectService.updateProject(anyLong(), any(Project.class))).thenReturn(project1);

        mockMvc.perform(put("/api/projects/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(project1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.projectName").value("Project One"))
                .andExpect(jsonPath("$.status").value(HttpStatus.OK.value()));
    }

    @Test
    void testDeleteProject() throws Exception {
        when(projectService.deleteProjectById(anyLong())).thenReturn(project1);

        mockMvc.perform(delete("/api/projects/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.projectName").value("Project One"))
                .andExpect(jsonPath("$.status").value(HttpStatus.OK.value()));
    }
}