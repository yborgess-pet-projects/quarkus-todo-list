package org.quarkus.demo.todolist.todos;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.inject.Inject;
import jakarta.json.JsonMergePatch;
import jakarta.json.JsonValue;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

import java.net.URI;
import java.util.List;

@Path("/api/v1/todos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TodoResource {

    @Inject
    TodoRepository repository;

    @Context
    UriInfo uriInfo;

    @Inject
    ObjectMapper mapper;

    @GET
    public Response getAll() {
        List<Todo> all = repository.findAll().list();

        return Response.ok()
                .entity(all)
                .build();
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") Long id) {
        Todo todo = repository.findTodoById(id);

        return Response.ok()
                .entity(todo)
                .build();
    }

    @POST
    @Transactional
    public Response createTodo(Todo todo) {
        repository.persist(todo);

        final URI location = uriInfo.getBaseUriBuilder()
                .path(TodoResource.class)
                .path(todo.getId().toString())
                .build();

        return Response.created(location)
                .entity(todo)
                .build();
    }

    @PATCH
    @Path("/{id}")
    @Consumes("application/merge-patch+json")
    @Transactional
    public Response edit(@PathParam("id") Long id, JsonMergePatch updates) {
        Todo found = repository.findTodoById(id);

        JsonValue target = mapper.convertValue(found, JsonValue.class);
        JsonValue patched = updates.apply(target);
        Todo patchedTodo = mapper.convertValue(patched, Todo.class);
        Todo merged = repository.getEntityManager().merge(patchedTodo);

        return Response.ok()
                .entity(merged)
                .build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Response putResource(@PathParam("id") Long id, Todo todo) {
        todo.setId(id);
        repository.getEntityManager().merge(todo);

        return Response.ok()
                .entity(todo)
                .build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response deleteTodo(@PathParam("id") Long id) {
        repository.deleteById(id);

        return Response.noContent()
                .build();
    }
}
