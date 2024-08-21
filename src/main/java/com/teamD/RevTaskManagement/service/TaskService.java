package com.teamD.RevTaskManagement.service;

import com.teamD.RevTaskManagement.dao.EmployeeDAO;
import com.teamD.RevTaskManagement.dao.TaskDAO;
import com.teamD.RevTaskManagement.exceptions.TaskNotFoundException;
import com.teamD.RevTaskManagement.model.Employee;
import com.teamD.RevTaskManagement.model.Task;
import com.teamD.RevTaskManagement.utilities.ModelUpdater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {

    @Autowired
    private TaskDAO taskRepository;

    @Autowired
    private EmployeeDAO employeeDAO;

    @Autowired
    private ModelUpdater modelUpdater;

    // Method to get a task by its ID
    public Task getTaskById(Long id) {
        return taskRepository.findById(id).orElseThrow(() ->
                new TaskNotFoundException("Task with ID " + id + " not found"));
    }

    // Method to create a new task
    public Task createTask(Task newTask) {
        List<Employee> employees = newTask.getAssignees().stream()
                .map(employee -> employeeDAO.findById(employee.getEmployeeId())
                        .orElseThrow(() -> new RuntimeException("Employee with ID " + employee.getEmployeeId() + " not found")))
                .collect(Collectors.toList());

        newTask.setAssignees(employees);
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
    public List<Task> getTasksByProjectId(Long projectId) {
        return taskRepository.findByProject_ProjectId(projectId);
    }
}
