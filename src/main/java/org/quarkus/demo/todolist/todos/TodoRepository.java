package org.quarkus.demo.todolist.todos;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.Response;


@ApplicationScoped
public class TodoRepository implements PanacheRepository<Todo> {
    public Todo findTodoById(Long id) {
        Todo result = findById(id);
        if (result == null) {
            throw new TodoNotFoundException(id, Response.Status.NOT_FOUND);
        }
        return result;
    }
}
