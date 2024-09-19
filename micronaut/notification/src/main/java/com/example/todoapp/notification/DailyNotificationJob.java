package com.example.todoapp.notification;

import com.example.todoapp.model.Todo;
import com.example.todoapp.model.TodoRepository;
import io.micronaut.email.BodyType;
import io.micronaut.email.Email;
import io.micronaut.email.EmailSender;
import io.micronaut.email.MultipartBody;
import io.micronaut.email.template.TemplateBody;
import io.micronaut.scheduling.annotation.Scheduled;
import io.micronaut.views.ModelAndView;
import jakarta.inject.Singleton;
import jakarta.transaction.Transactional;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Singleton
public class DailyNotificationJob {

    private final EmailSender<?, ?> emailSender;
    private final TodoRepository repository;

    public DailyNotificationJob(EmailSender<?, ?> emailSender, TodoRepository repository) {
        this.emailSender = emailSender;
        this.repository = repository;
    }

    @Transactional
    @Scheduled(cron = "0 0 8 * * ?") // Every day at 8 AM
    void checkTodosAndSendNotifications() {
        List<Todo> todos = repository.findAllByDueDateAndArchived(LocalDate.now(), false);

        if (!todos.isEmpty()) {
            Map<String, Object> model = new HashMap<>();
            model.put("todos", todos);

            emailSender.send(Email.builder()
                    .from("sender@example.com")
                    .to("user@example.com")
                    .subject("Reminder: Tasks Due Today")
                    .body(new MultipartBody(
                            new TemplateBody<>(BodyType.HTML, new ModelAndView<>("todoReminderHtml", model)),
                            new TemplateBody<>(BodyType.TEXT, new ModelAndView<>("todoReminderText", model))
                    )));
        }
    }
}