package com.teamD.RevTaskManagement.servicetest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.teamD.RevTaskManagement.controller.TaskController;
import com.teamD.RevTaskManagement.model.Task;
import com.teamD.RevTaskManagement.service.TaskService;
import com.teamD.RevTaskManagement.exceptions.TaskNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TaskController.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService taskService;

    @InjectMocks
    private TaskController taskController;

    private Task task;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        task = new Task();
        task.setTaskId(1L);
        task.setTaskName("Test Task");
        // Set other fields as needed
    }

    @Test
    void testGetAllTasks() throws Exception {
        List<Task> tasks = Arrays.asList(task);

        when(taskService.fetchAllTasks()).thenReturn(tasks);

        mockMvc.perform(get("/api/tasks"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].taskName").value("Test Task"));
    }

    @Test
    void testGetTaskById() throws Exception {
        when(taskService.getTaskById(1L)).thenReturn(task);

        mockMvc.perform(get("/api/tasks/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.taskName").value("Test Task"));
    }

    @Test
    void testGetTaskById_NotFound() throws Exception {
        when(taskService.getTaskById(1L)).thenThrow(new TaskNotFoundException("Task with ID 1 not found"));

        mockMvc.perform(get("/api/tasks/1"))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof TaskNotFoundException))
                .andExpect(result -> assertEquals("Task with ID 1 not found", result.getResolvedException().getMessage()));
    }


    @Test
    void testCreateTask() throws Exception {
        Task newTask = new Task();
        newTask.setTaskName("New Task");

        when(taskService.createTask(any(Task.class))).thenReturn(newTask);

        mockMvc.perform(post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(newTask)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.taskName").value("New Task"));
    }

    @Test
    void testUpdateTask() throws Exception {
        Task updatedTask = new Task();
        updatedTask.setTaskName("Updated Task");

        when(taskService.updateTask(eq(1L), any(Task.class))).thenReturn(updatedTask);

        mockMvc.perform(put("/api/tasks/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updatedTask)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.taskName").value("Updated Task"));
    }

    @Test
    void testUpdateTask_NotFound() throws Exception {
        Task updatedTask = new Task();
        updatedTask.setTaskName("Updated Task");

        when(taskService.updateTask(eq(1L), any(Task.class)))
                .thenThrow(new TaskNotFoundException("Task with ID 1 not found"));

        mockMvc.perform(put("/api/tasks/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updatedTask)))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof TaskNotFoundException))
                .andExpect(result -> assertEquals("Task with ID 1 not found", result.getResolvedException().getMessage()));
    }

    @Test
    void testDeleteTask() throws Exception {
        doNothing().when(taskService).deleteTask(1L);

        mockMvc.perform(delete("/api/tasks/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDeleteTask_NotFound() throws Exception {
        doThrow(new TaskNotFoundException("Task with ID 1 not found")).when(taskService).deleteTask(1L);

        mockMvc.perform(delete("/api/tasks/1"))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof TaskNotFoundException))
                .andExpect(result -> assertEquals("Task with ID 1 not found", result.getResolvedException().getMessage()));
    }
}
