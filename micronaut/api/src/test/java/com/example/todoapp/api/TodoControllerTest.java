package com.example.todoapp.api;

import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import com.agorapulse.gru.Gru;

import static org.junit.jupiter.api.Assertions.*;

@MicronautTest
class TodoControllerTest {

    @Inject Gru gru;

    @Test
    void createAndQueryTodos() throws Throwable {
        gru.verify(t -> t
                .post("/todos", r -> r.json("newTodoRequest.json"))
                .expect(e -> e.json("newTodoResponse.json"))
        );

        gru.verify(t -> t
                .get("/todos")
                .expect(e -> e.json("allTodosResponse.json"))
        );
    }

}