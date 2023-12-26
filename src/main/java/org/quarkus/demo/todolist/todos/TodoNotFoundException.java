package org.quarkus.demo.todolist.todos;

import jakarta.ws.rs.core.Response;
import org.quarkus.demo.todolist.TodoListException;

public class TodoNotFoundException extends TodoListException {

    public static final String MESSAGE = "The Todo with id %d was not found.";

    public TodoNotFoundException(long id, Response.StatusType statusType) {
        super(String.format(MESSAGE, id), statusType);
    }
}