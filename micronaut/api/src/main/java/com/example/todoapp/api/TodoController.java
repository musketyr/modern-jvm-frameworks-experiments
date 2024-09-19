package com.example.todoapp.api;

import com.example.todoapp.model.Todo;
import com.example.todoapp.model.TodoRepository;
import io.micronaut.http.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller("/todos")
public class TodoController {

    private final TodoRepository repository;

    public TodoController(TodoRepository repository) {
        this.repository = repository;
    }

    @Get
    public List<Todo> getAll() {
        return repository.findAll();
    }

    @Post
    public Todo create(@Body Todo todo) {
        return repository.save(todo);
    }

    @Put("/{id}")
    public Optional<Todo> update(@PathVariable("id") Long id, @Body Todo todo) {
        return repository.findById(id).map(entity -> {
            entity.setTask(todo.getTask());
            entity.setDueDate(todo.getDueDate());
            repository.update(entity);
            return entity;
        });
    }

    @Post("/{id}/archive")
    public void archive(@PathVariable("id") Long id) {
        repository.findById(id).ifPresent(entity -> {
            entity.setArchived(true);
            repository.update(entity);
        });
    }
}
