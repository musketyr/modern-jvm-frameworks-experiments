
package com.example.todoapp;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.util.List;

/**
 * This class implements REST endpoints to interact with TODOs. The following
 * operations are supported:
 *
 * <ul>
 *     <li>GET /todo - Retrieves all TODOs</li>
 *     <li>POST /todo - Creates a new TODO</li>
 *     <li>PUT /todo/{id} - Updates an existing TODO</li>
 *     <li>POST /todo/{id}/archive - Archives an existing TODO</li>
 * </ul>
 *
 */
@Path("todo")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TodoResource {

    @PersistenceContext(unitName = "todos")
    private EntityManager entityManager;

    @GET
    public List<Todo> getTodos() {
        return entityManager.createNamedQuery("getTodos", Todo.class).getResultList();
    }

    @POST
    @Transactional
    public Todo create(Todo todo) {
        entityManager.persist(todo);
        return todo;
    }

    @PUT
    @Path("{id}")
    @Transactional
    public Todo update(@PathParam("id") int id, Todo todo) {
        Todo entity = entityManager.find(Todo.class, id);
        if (entity == null) {
            throw new WebApplicationException("Todo not found", 404);
        }
        entity.setTask(todo.getTask());
        entity.setDueDate(todo.getDueDate());
        entityManager.persist(entity);
        return entity;
    }

    @POST
    @Path("{id}/archive")
    @Transactional
    public void archive(@PathParam("id") int id) {
        Todo entity = entityManager.find(Todo.class, id);
        if (entity == null) {
            throw new WebApplicationException("Todo not found", 404);
        }
        entity.setArchived(true);
        entityManager.persist(entity);
    }

}
