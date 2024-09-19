package com.example.todoapp.notification;

import com.example.todoapp.model.Todo;
import com.example.todoapp.model.TodoRepository;
import io.micronaut.test.annotation.MockBean;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@MicronautTest
@Transactional
class DailyNotificationJobTest {

    @MockBean(TodoRepository.class)
    TodoRepository todoRepository = mock(TodoRepository.class);

    @Inject DailyNotificationJob job;
    @Inject MailHogClient mailHogClient;

    @Test
    void testCheckTodosAndSendNotifications() {
        when(todoRepository.findAllByDueDateAndArchived(any(), eq(false))).thenReturn(Collections.singletonList(
                Todo.of("Buy milk", LocalDateTime.now())
        ));

        job.checkTodosAndSendNotifications();

        assertTrue(mailHogClient.getMessages().getItems().getFirst().getContent().getBody().contains("Buy milk"));
    }

}