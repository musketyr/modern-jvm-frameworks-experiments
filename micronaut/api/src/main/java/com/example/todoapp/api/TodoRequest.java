package com.example.todoapp.api;

import io.micronaut.core.annotation.Introspected;

import java.time.LocalDateTime;

@Introspected
public record TodoRequest(String task, LocalDateTime dueDate) { }
