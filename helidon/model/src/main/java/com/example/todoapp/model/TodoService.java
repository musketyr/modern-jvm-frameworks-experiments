package com.example.todoapp.model;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@ApplicationScoped
public class TodoService {

    @PersistenceContext(unitName = "todos")
    protected EntityManager entityManager;

    public List<Todo> findAllToday() {
        return entityManager.createNamedQuery("getAllTodayTodos", Todo.class).getResultList();
    }

    public List<Todo> findAll() {
        return entityManager.createNamedQuery("getAllTodos", Todo.class).getResultList();
    }

    public Todo create(Todo todo) {
        entityManager.persist(todo);
        return todo;
    }

    public Todo findById(Long id) {
        return entityManager.createNamedQuery("getTodoById", Todo.class).getSingleResult();
    }

    public Todo update(Todo entity) {
        return entityManager.merge(entity);
    }
}
