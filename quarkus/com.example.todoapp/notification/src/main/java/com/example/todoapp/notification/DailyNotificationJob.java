package com.example.todoapp.notification;

import com.example.todoapp.model.Todo;
import io.quarkus.mailer.Mail;
import io.quarkus.mailer.Mailer;
import io.quarkus.qute.Template;
import io.quarkus.scheduler.Scheduled;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class DailyNotificationJob {

    @Inject
    Mailer mailer;

    @Inject
    Template todoReminder;  // Inject Qute template

    @Scheduled(cron = "0 0 8 * * ?") // Every day at 8 AM
    void checkTodosAndSendNotifications() {
        List<Todo> todos = Todo.list("dueDate = ?1 and archived = false", LocalDate.now());

        for (Todo todo : todos) {
            Map<String, Object> emailData = new HashMap<>();
            emailData.put("task", todo.getTask());
            emailData.put("dueDate", todo.getDueDate());

            String emailBody = todoReminder
                    .data(emailData)     // Populate the template with task data
                    .render();           // Render the HTML content

            mailer.send(
                    Mail.withHtml(
                            "user@example.com",
                            "Reminder: Task Due Today",
                            emailBody
                    )
            );
        }
    }
}
