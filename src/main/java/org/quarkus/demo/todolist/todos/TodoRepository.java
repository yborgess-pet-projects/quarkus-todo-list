package org.quarkus.demo.todolist.todos;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.LockModeType;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

import java.util.Optional;


@ApplicationScoped
public class TodoRepository implements PanacheRepository<Todo> {

    @Context
    UriInfo uriInfo;

    public Todo findTodoById(Long id) {
        Todo result = findById(id);
        if (result == null) {
            throw new TodoNotFoundException(id, Response.Status.NOT_FOUND);
        }
        return result;
    }
}
