package com.teamD.RevTaskManagement.service;

import com.teamD.RevTaskManagement.dao.TaskDAO;
import com.teamD.RevTaskManagement.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private TaskDAO taskDAO;

    public Task createTask(Task task) {
        return taskDAO.save(task);
    }

    public Optional<Task> getTaskById(Long id) {
        return taskDAO.findById(id);
    }

    public Task updateTask(Long id, Task task) {
        if (taskDAO.existsById(id)) {
            task.setTaskId(id);
            return taskDAO.save(task);
        }
        return null;
    }

    public void deleteTask(Long id) {
        taskDAO.deleteById(id);
    }
}
