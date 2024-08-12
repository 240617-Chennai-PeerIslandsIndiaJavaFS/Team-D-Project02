package com.teamD.RevTaskManagement.ServiceTest;

import com.teamD.RevTaskManagement.dao.TaskDAO;
import com.teamD.RevTaskManagement.model.Task;
import com.teamD.RevTaskManagement.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TaskServiceTest {

    @Mock
    private TaskDAO taskDAO;

    @InjectMocks
    private TaskService taskService;

    private Task task;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        task = new Task();
        task.setTaskId(1L);
        task.setTaskName("Test Task");
        task.setDescription("This is a test task.");
        task.setStartDate(new Date());
        task.setEndDate(new Date());
        task.setTimestamp(new Date());
    }

    @Test
    public void testCreateTask() {
        when(taskDAO.save(any(Task.class))).thenReturn(task);

        Task createdTask = taskService.createTask(task);

        assertNotNull(createdTask);
        assertEquals(task.getTaskName(), createdTask.getTaskName());
        verify(taskDAO, times(1)).save(task);
    }

    @Test
    public void testGetTaskById() {
        when(taskDAO.findById(1L)).thenReturn(Optional.of(task));

        Optional<Task> foundTask = taskService.getTaskById(1L);

        assertTrue(foundTask.isPresent());
        assertEquals(task.getTaskName(), foundTask.get().getTaskName());
        verify(taskDAO, times(1)).findById(1L);
    }

    @Test
    public void testUpdateTask() {
        when(taskDAO.existsById(1L)).thenReturn(true);
        when(taskDAO.save(any(Task.class))).thenReturn(task);

        Task updatedTask = taskService.updateTask(1L, task);

        assertNotNull(updatedTask);
        assertEquals(task.getTaskName(), updatedTask.getTaskName());
        verify(taskDAO, times(1)).existsById(1L);
        verify(taskDAO, times(1)).save(task);
    }

    @Test
    public void testUpdateTask_NotFound() {
        when(taskDAO.existsById(1L)).thenReturn(false);

        Task updatedTask = taskService.updateTask(1L, task);

        assertNull(updatedTask);
        verify(taskDAO, times(1)).existsById(1L);
        verify(taskDAO, never()).save(any(Task.class));
    }

    @Test
    public void testDeleteTask() {
        doNothing().when(taskDAO).deleteById(1L);

        taskService.deleteTask(1L);

        verify(taskDAO, times(1)).deleteById(1L);
    }
}
