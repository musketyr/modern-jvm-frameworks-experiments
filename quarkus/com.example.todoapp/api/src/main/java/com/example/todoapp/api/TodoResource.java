package com.example.todoapp.api;

import com.example.todoapp.model.Todo;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.util.List;

@Path("/todos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TodoResource {

    @GET
    public List<Todo> getAll() {
        return Todo.listAll();
    }

    @POST
    @Transactional
    public Todo create(Todo todo) {
        todo.persist();
        return todo;
    }

    @PUT
    @Path("{id}")
    @Transactional
    public Todo update(@PathParam("id") Long id, Todo todo) {
        Todo entity = Todo.findById(id);
        if (entity == null) {
            throw new WebApplicationException("Todo not found", 404);
        }
        entity.setTask(todo.getTask());
        entity.setDueDate(todo.getDueDate());
        return entity;
    }

    @POST
    @Path("{id}/archive")
    @Transactional
    public void archive(@PathParam("id") Long id) {
        Todo entity = Todo.findById(id);
        if (entity == null) {
            throw new WebApplicationException("Todo not found", 404);
        }
        entity.setArchived(true);
    }
}
