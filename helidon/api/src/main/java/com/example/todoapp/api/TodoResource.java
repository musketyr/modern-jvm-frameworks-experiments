package com.example.todoapp.api;

import java.util.List;

import com.example.todoapp.model.Todo;
import com.example.todoapp.model.TodoService;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;

@Path("/todos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TodoResource {

    @Inject
    private TodoService todoService;

    @GET
    public List<Todo> getAll() {
        return todoService.findAll();
    }

    @POST
    @Transactional
    public Todo create(Todo todo) {
        return todoService.create(todo);
    }

    @PUT
    @Path("{id}")
    @Transactional
    public Todo update(@PathParam("id") Long id, Todo todo) {
        Todo entity = todoService.findById(id);
        if (entity == null) {
            throw new WebApplicationException("Todo not found", 404);
        }
        entity.setTask(todo.getTask());
        entity.setDueDate(todo.getDueDate());
        return todoService.update(entity);
    }

    @POST
    @Path("{id}/archive")
    @Transactional
    public void archive(@PathParam("id") Long id) {
        Todo entity = todoService.findById(id);
        if (entity == null) {
            throw new WebApplicationException("Todo not found", 404);
        }
        entity.setArchived(true);
        todoService.update(entity);
    }
}
