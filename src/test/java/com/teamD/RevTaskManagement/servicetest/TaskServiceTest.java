package com.teamD.RevTaskManagement.servicetest;

import com.teamD.RevTaskManagement.dao.TaskDAO;
import com.teamD.RevTaskManagement.exceptions.TaskNotFoundException;
import com.teamD.RevTaskManagement.model.Task;
import com.teamD.RevTaskManagement.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TaskServiceTest {

    @Mock
    private TaskDAO taskRepository;

    @InjectMocks
    private TaskService taskService;

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
    void testCreateTask() {
        // Mocking the taskRepository.save() method
        when(taskRepository.save(task)).thenReturn(task);

        // Call the method to test
        Task createdTask = taskService.createTask(task);

        // Verify the results
        assertNotNull(createdTask);
        assertEquals(1L, createdTask.getTaskId());
        assertEquals("Test Task", createdTask.getTaskName());

        // Verify that the repository method was called
        verify(taskRepository, times(1)).save(task);
    }

    @Test
    void testGetTaskById() {
        // Mocking the taskRepository.findById() to return an Optional containing the task
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        // Call the method to test
        Task foundTask = taskService.getTaskById(1L);

        // Verify the results
        assertNotNull(foundTask);
        assertEquals(1L, foundTask.getTaskId());
        assertEquals("Test Task", foundTask.getTaskName());

        // Verify that the repository method was called
        verify(taskRepository, times(1)).findById(1L);
    }

    @Test
    void testGetTaskById_TaskNotFound() {
        // Mocking the taskRepository.findById() to return an empty Optional
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        // Verify that the exception is thrown
        assertThrows(TaskNotFoundException.class, () -> taskService.getTaskById(1L));

        // Verify that the repository method was called
        verify(taskRepository, times(1)).findById(1L);
    }

    @Test
    void testUpdateTask() {
        // Mocking the taskRepository.findById() to return an Optional containing the task
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        // Creating an updatedTask with new values
        Task updatedTask = new Task();
        updatedTask.setTaskName("Updated Task Name");
        updatedTask.setDescription("Updated Description");

        // Mocking the taskRepository.save() method to return the updated task
        when(taskRepository.save(any(Task.class))).thenReturn(updatedTask);

        // Call the method to test
        Task result = taskService.updateTask(1L, updatedTask);

        // Verify the results
        assertNotNull(result);
        assertEquals("Updated Task Name", result.getTaskName());
        assertEquals("Updated Description", result.getDescription());

        // Verify that the repository methods were called
        verify(taskRepository, times(1)).findById(1L);
        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    void testUpdateTask_TaskNotFound() {
        // Mocking the taskRepository.findById() to return an empty Optional
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        // Verify that the exception is thrown
        assertThrows(TaskNotFoundException.class, () -> taskService.updateTask(1L, new Task()));

        // Verify that the repository method was called
        verify(taskRepository, times(1)).findById(1L);
    }

    @Test
    void testDeleteTask() {
        // Mocking the taskRepository.findById() to return an Optional containing the task
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        // Call the method to test
        taskService.deleteTask(1L);

        // Verify that the repository delete method was called
        verify(taskRepository, times(1)).delete(task);
    }

    @Test
    void testDeleteTask_TaskNotFound() {
        // Mocking the taskRepository.findById() to return an empty Optional
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        // Verify that the exception is thrown
        assertThrows(TaskNotFoundException.class, () -> taskService.deleteTask(1L));

        // Verify that the repository method was called
        verify(taskRepository, times(1)).findById(1L);
    }

    @Test
    void testFetchAllTasks() {
        // Creating a list of tasks
        Task task2 = new Task();
        task2.setTaskId(2L);
        task2.setTaskName("Another Task");

        List<Task> tasks = Arrays.asList(task, task2);

        // Mocking the taskRepository.findAll() to return the list of tasks
        when(taskRepository.findAll()).thenReturn(tasks);

        // Call the method to test
        List<Task> fetchedTasks = taskService.fetchAllTasks();

        // Verify the results
        assertNotNull(fetchedTasks);
        assertEquals(2, fetchedTasks.size());
        assertTrue(fetchedTasks.contains(task));
        assertTrue(fetchedTasks.contains(task2));

        // Verify that the repository method was called
        verify(taskRepository, times(1)).findAll();
    }
}
