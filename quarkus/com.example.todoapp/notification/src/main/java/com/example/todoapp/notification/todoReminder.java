package com.example.todoapp.notification;

import java.util.List;

import com.example.todoapp.model.Todo;

import io.quarkus.mailer.MailTemplate.MailTemplateInstance;
import io.quarkus.qute.CheckedTemplate;

/**
 * Type-safe representation of
 * {@code src/main/resources/templates/todoReminder.html} that will be used as a mail template.
 */
@CheckedTemplate // FIXME: this annotation should not be necessary; see https://github.com/quarkusio/quarkus/issues/43518
public record todoReminder(List<Todo> todos) implements MailTemplateInstance {

}