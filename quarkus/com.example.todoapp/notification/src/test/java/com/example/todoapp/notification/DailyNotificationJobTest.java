package com.example.todoapp.notification;

import com.example.todoapp.model.Todo;
import io.quarkiverse.mailpit.test.InjectMailbox;
import io.quarkiverse.mailpit.test.Mailbox;
import io.quarkiverse.mailpit.test.WithMailbox;
import io.quarkiverse.mailpit.test.model.Message;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
@WithMailbox
@Transactional
class DailyNotificationJobTest {

    @InjectMailbox
    Mailbox mailbox;

    @Inject
    DailyNotificationJob dailyNotificationJob;

    @Test
    void testCheckTodosAndSendNotifications() {
        Todo todo = new Todo();
        todo.setDueDate(LocalDateTime.now());
        todo.setArchived(false);
        Todo.persist(todo);

        // Call the method to test
        dailyNotificationJob.checkTodosAndSendNotifications();

        // Verify the mail was sent
        Message message = mailbox.findFirst("user@example.com");
        assertNotNull(message);
        assertEquals("Reminder: Tasks Due Today", message.getSubject());
        assertNotNull(message.getHTML());
        assertTrue(message.getHTML().contains("Tasks Due Today"));
    }

}