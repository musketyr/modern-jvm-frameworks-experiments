
package com.example.todoapp;


import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;

import java.time.LocalDateTime;


@Entity(name = "Todo")
@Table(name = "TODO")
@Access(AccessType.PROPERTY)
@NamedQueries({
        @NamedQuery(name = "getTodos",
                    query = "SELECT p FROM Todo p"),
        @NamedQuery(name = "getActiveTasksForToday",
                    query = "SELECT p FROM Todo p WHERE p.archived = false and p.dueDate = CURRENT_DATE")
})
public class Todo {

    private int id;

    private String task;

    private LocalDateTime dueDate;

    private boolean archived;

    public Todo() {
    }

    @Id
    @Column(name = "ID", nullable = false, updatable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic(optional = false)
    @Column(name = "TASK", nullable = false)
    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    @Column(name = "DUE_DATE")
    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    @Column(name = "ARCHIVED")
    public boolean isArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }





}
