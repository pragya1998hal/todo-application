package com.example.todo.dto;

import jakarta.validation.constraints.NotBlank;

public class TaskDTO {
    private int id;

    @NotBlank(message = "Description cannot be empty")
    private String description;

    private boolean completed;

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
