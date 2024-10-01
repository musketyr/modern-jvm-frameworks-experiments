package com.example.todoapp.model;

import java.time.LocalDateTime;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;

@Entity
@Access(AccessType.FIELD)
@NamedQueries({
        @NamedQuery(name = "getTodoById", query = "SELECT t FROM Todo t WHERE t.id = :id"),
        @NamedQuery(name = "getAllTodos", query = "SELECT t FROM Todo t"),
        @NamedQuery(name = "getAllTodayTodos",
                    query = "SELECT t FROM Todo t WHERE DATE(t.dueDate) = CURRENT_DATE and t.archived = false")
})
public class Todo {

    @GeneratedValue @Id
    private Long id;
    private String task;
    private LocalDateTime dueDate;
    private boolean archived;

    public static Todo of(String task, LocalDateTime dueDate) {
        Todo todo = new Todo();
        todo.setTask(task);
        todo.setDueDate(dueDate);
        return todo;
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

    public void setArchived(boolean archived) {
        this.archived = archived;
    }

}
