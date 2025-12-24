package com.example.todo.controller;

import com.example.todo.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;

@Controller
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping
    public String getAllTasks(Model model) {
        model.addAttribute("tasks", taskService.getAllTasks());
        return "index";
    }

    @GetMapping("/add")
    public String showAddTaskForm() {
        return "add-task";
    }

    @PostMapping("/add")
    public String addTask(@RequestParam String description, @RequestParam String priority, @RequestParam String dueDate) {
        taskService.addTask(description, priority, LocalDate.parse(dueDate));
        return "redirect:/tasks";
    }

    @GetMapping("/edit/{id}")
    public String showEditTaskForm(@PathVariable int id, Model model) {
        model.addAttribute("task", taskService.getTaskById(id));
        return "edit-task";
    }

    @PostMapping("/edit")
    public String editTask(@RequestParam int id, @RequestParam String description,
                           @RequestParam(defaultValue = "false") boolean completed,
                           @RequestParam String priority, @RequestParam String dueDate) {
        try {
            LocalDate parsedDueDate = LocalDate.parse(dueDate);
            taskService.updateTask(id, description, completed, priority, parsedDueDate);
        } catch (Exception e) {
            // Log error and handle invalid input
            return "redirect:/tasks";
        }
        return "redirect:/tasks";
    }


    @GetMapping("/delete/{id}")
    public String deleteTask(@PathVariable int id) {
        taskService.deleteTask(id);
        return "redirect:/tasks";
    }

    @PostMapping("/search")
    public String searchTasks(@RequestParam String query, Model model) {
        model.addAttribute("tasks", taskService.searchTasks(query));
        return "index";
    }
}
