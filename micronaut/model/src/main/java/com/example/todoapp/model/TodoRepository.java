package com.example.todoapp.model;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TodoRepository extends CrudRepository<Todo, Long> {


    default List<Todo> findAllByDueDateAndArchived(LocalDate dueDate, boolean archived) {
        return findAllByDueDateBetweenAndArchived(dueDate.atStartOfDay(), dueDate.atTime(23, 59, 59), archived);
    }

    List<Todo> findAllByDueDateBetweenAndArchived(LocalDateTime from, LocalDateTime to, boolean archived);
}
