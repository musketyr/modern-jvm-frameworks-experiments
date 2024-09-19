package com.example.todoapp.model;

import io.micronaut.serde.annotation.Serdeable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
@Serdeable
public class Todo {

    public static Todo of(String task, LocalDateTime dueDate) {
        Todo todo = new Todo();
        todo.setTask(task);
        todo.setDueDate(dueDate);
        return todo;
    }

    @GeneratedValue @Id
    private Long id;
    private String task;
    private LocalDateTime dueDate;
    private boolean archived;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public boolean isArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }

}
