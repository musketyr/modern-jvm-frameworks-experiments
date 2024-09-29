package com.example.todoapp;

import io.helidon.microprofile.scheduling.Scheduled;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

public class DailyNotificationJob {

    @PersistenceContext(unitName = "todos")
    private EntityManager entityManager;

    @Scheduled("0 0 8 * * ?") // Every day at 8 AM
    void checkTodosAndSendNotifications() {
        List<Todo> todos = entityManager.createNamedQuery("getActiveTasksForToday", Todo.class).getResultList();

        if (!todos.isEmpty()) {
            // send email
        }
    }

}
