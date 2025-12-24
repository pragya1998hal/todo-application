package com.example.todo.service;


import com.example.todo.entity.Task;
import com.example.todo.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Task getTaskById(int id) {
        return taskRepository.findById(id).orElse(null);
    }

    public Task addTask(String description, String priority, LocalDate dueDate) {
        Task task = new Task(0, description, false, priority, dueDate);
        return taskRepository.save(task);
    }

    public boolean updateTask(int id, String description, boolean completed, String priority, LocalDate dueDate) {
        Task task = getTaskById(id);
        if (task != null) {
            task.setDescription(description);
            task.setCompleted(completed);
            task.setPriority(priority);
            task.setDueDate(dueDate);
            taskRepository.save(task);
            return true;
        }
        return false;
    }

    public boolean deleteTask(int id) {
        if (taskRepository.existsById(id)) {
            taskRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<Task> searchTasks(String query) {
        return taskRepository.findByDescriptionContaining(query);
    }
}
