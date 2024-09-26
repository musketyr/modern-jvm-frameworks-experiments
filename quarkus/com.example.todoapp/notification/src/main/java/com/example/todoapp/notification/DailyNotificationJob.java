package com.example.todoapp.notification;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import com.example.todoapp.model.Todo;

import io.quarkus.logging.Log;
import io.quarkus.scheduler.Scheduled;

public class DailyNotificationJob {

    @Scheduled(cron = "0 0 8 * * ?") // Every day at 8 AM
    void checkTodosAndSendNotifications() {
        List<Todo> todos = Todo.list("DATE(dueDate) = :today and archived = false", Map.of("today", LocalDate.now()));

        if (!todos.isEmpty()) {
            new todoReminder(todos)
                    .from("admin@example.net")
                    .to("user@example.com")
                    .subject("Reminder: Tasks Due Today")
                    .send()
                    // send() returns Uni - a reactive/async type, we need to wait for result if
                    // blocking API is used
                    .await().indefinitely();
            Log.infof("Daily notification sent for %s todos", todos.size());
        }
    }
}