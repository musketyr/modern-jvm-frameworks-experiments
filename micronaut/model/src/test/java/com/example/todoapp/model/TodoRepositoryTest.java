package com.example.todoapp.model;

import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@MicronautTest
class TodoRepositoryTest {

    @Inject TodoRepository repository;

    @Test
    void saveAndQueryEntities() {
        LocalDate referenceDate = LocalDate.now();

        repository.save(Todo.of("Buy milk", referenceDate.minusDays(1).atTime(12, 0, 0)));
        repository.save(Todo.of("Buy bread", referenceDate.plusDays(1).atTime(12, 0, 0)));
        repository.save(Todo.of("Relax", referenceDate.atTime(12, 0, 0)));

        assertEquals(1, repository.findAllByDueDateAndArchived(referenceDate, false).size());
    }

}