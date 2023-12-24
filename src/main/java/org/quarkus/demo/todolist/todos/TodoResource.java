package org.quarkus.demo.todolist.todos;

import jakarta.inject.Inject;
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
        Todo todo = repository.findById(id);

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
    public Response edit(@PathParam("id") Long id, Todo todo) {
        Todo found = repository.findById(id);

        if (todo.getTitle() != null) {
            found.setTitle(todo.getTitle());
        }

        if (todo.getCompleted() != null) {
            found.setCompleted(todo.getCompleted());
        }

        return Response.ok()
                .entity(found)
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
