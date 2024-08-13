package com.teamD.RevTaskManagement.service;

import com.teamD.RevTaskManagement.dao.TaskDAO;
import com.teamD.RevTaskManagement.exceptions.TaskNotFoundException;
import com.teamD.RevTaskManagement.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

    @Autowired
    private TaskDAO taskRepository;

    // Method to get a task by its ID
    public Task getTaskById(Long id) {
        return taskRepository.findById(id).orElseThrow(() ->
                new TaskNotFoundException("Task with ID " + id + " not found"));
    }

    // Method to create a new task
    public Task createTask(Task newTask) {
        return taskRepository.save(newTask);
    }

    // Method to update an existing task
    public Task updateTask(Long id, Task updatedTask) {
        Task existingTask = taskRepository.findById(id).orElseThrow(() ->
                new TaskNotFoundException("Task with ID " + id + " not found"));

        // Update fields only if the new value is not null, otherwise keep the old value
        if (updatedTask.getTaskName() != null) {
            existingTask.setTaskName(updatedTask.getTaskName());
        }
        if (updatedTask.getDescription() != null) {
            existingTask.setDescription(updatedTask.getDescription());
        }
        if (updatedTask.getStartDate() != null) {
            existingTask.setStartDate(updatedTask.getStartDate());
        }
        if (updatedTask.getEndDate() != null) {
            existingTask.setEndDate(updatedTask.getEndDate());
        }
        if (updatedTask.getTimestamp() != null) {
            existingTask.setTimestamp(updatedTask.getTimestamp());
        }
        if (updatedTask.getProject() != null) {
            existingTask.setProject(updatedTask.getProject());
        }
        if (updatedTask.getAssignees() != null) {
            existingTask.setAssignees(updatedTask.getAssignees());
        }
        if (updatedTask.getComments() != null) {
            existingTask.setComments(updatedTask.getComments());
        }
        if (updatedTask.getTimestamps() != null) {
            existingTask.setTimestamps(updatedTask.getTimestamps());
        }

        return taskRepository.save(existingTask);
    }

    // Method to delete a task by its ID
    public void deleteTask(Long id) {
        Task task = taskRepository.findById(id).orElseThrow(() ->
                new TaskNotFoundException("Task with ID " + id + " not found"));

        taskRepository.delete(task);
    }
}
