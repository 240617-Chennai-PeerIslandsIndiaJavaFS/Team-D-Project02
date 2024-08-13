package com.teamD.RevTaskManagement.service;

import com.teamD.RevTaskManagement.dao.TaskDAO;
import com.teamD.RevTaskManagement.exceptions.TaskNotFoundException;
import com.teamD.RevTaskManagement.model.Task;
import com.teamD.RevTaskManagement.utilities.ModelUpdater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    @Autowired
    private TaskDAO taskRepository;

    @Autowired
    private ModelUpdater modelUpdater;

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

        // Use ModelUpdater to update fields
        modelUpdater.updateFields(existingTask, updatedTask);

        return taskRepository.save(existingTask);
    }

    // Method to delete a task by its ID
    public void deleteTask(Long id) {
        Task task = taskRepository.findById(id).orElseThrow(() ->
                new TaskNotFoundException("Task with ID " + id + " not found"));

        taskRepository.delete(task);
    }

    // Method to fetch all tasks
    public List<Task> fetchAllTasks() {
        return taskRepository.findAll();
    }
}
